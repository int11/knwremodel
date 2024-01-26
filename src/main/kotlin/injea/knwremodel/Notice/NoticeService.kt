package injea.knwremodel.Notice

import injea.knwremodel.College.CollegeRepository
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

@Service
class NoticeService(private val noticeRepo: NoticeRepository, private val CollegeRepo: CollegeRepository) {
    private val pattern = Pattern.compile("\\((\\d+)\\)")
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    private val parser = JSONParser()

    fun updateAll() {
//        updateEvent()
        updateNotice(3)

    }

    @Transactional
    fun updateNotice(maxPage: Int) {
        val notices: MutableList<Notice> = mutableListOf()

        for (e in CollegeRepo.findAll()) {
            val LastPage = getLastpage(e.url, maxPage)

            for (page in LastPage downTo 1) {
                val document = Jsoup.connect("${e.url}?paginationInfo.currentPageNo=${page}").get()
                val contents = document.getElementsByClass("tbody").select("ul")

                for (content in contents.reversed()) {
                    val columnNumber = content.select("li").first()!!.text()
                    if (columnNumber == "필독" || columnNumber == "공지"){
                        continue
                    }

                    val boardId = columnNumber.toLong()

                    if (noticeRepo.existsByMajorAndBoardId(e.major, boardId)) {
                        continue
                    }

                    val titleElements = content.select("a")

                    val (regdate, type, body, img, html) = crawlingbody(e.url, titleElements)


                    notices.add(Notice(
                                    boardId,
                                    titleElements.attr("title"),
                                    e.major,
                                    type as String,
                                    content.select("li.sliceDot6").text(),
                                    regdate as LocalDateTime,
                                    content.select("li.sliceDot6").next().next().text().replace(",", "").toLong(),
                                    body as String,
                                    img as String,
                                    html as String
                                )
                    )
                }
            }
            noticeRepo.saveAll(notices)
        }
    }


    @Transactional
    fun updateEvent(maxPage: Int) {
        val url = "https://web.kangnam.ac.kr/menu/e4058249224f49ab163131ce104214fb.do"
        val notices: MutableList<Notice> = mutableListOf()

        val LastPage = getLastpage(url, maxPage)

        for (page in LastPage downTo 1) {
            val document = Jsoup.connect("${url}?paginationInfo.currentPageNo=${page}").get()
            val contents = document.getElementsByClass("tbody").select("ul")

            for (content in contents) {
                val titleElements = content.select("a")

                if (noticeRepo.existsByTitle(titleElements.attr("title")))
                    continue

                //게시물 타입, 내용, 사진 크롤링
                val (regdate, type, body, img, html) = crawlingbody(url, titleElements)

                notices.add(
                    Notice(
                        null,
                        titleElements.attr("title"),
                        "행사/안내",
                        type as String,
                        content.select("li dd span").first()!!.text().replace("작성자 ", ""),
                        regdate as LocalDateTime,
                        content.select("li dd span")
                            .next().next().text().replace("조회수 ", "").replace(",", "").toLong(),
                        body as String,
                        img as String,
                        html as String
                    )
                )
            }
        }
        noticeRepo.saveAll(notices)
    }

    private fun getLastpage(url: String, maxPage: Int): Int {
        val document = Jsoup.connect("${url}?paginationInfo.currentPageNo=${maxPage}").get()

        val lp = document.getElementsByClass("pagination create_mob_pagination").select("a").last()!!.attr("onclick")

        val matcher = pattern.matcher(lp)
        if (matcher.find()) {
            return matcher.group(1).toInt()
        }
        return 0
    }

    private fun crawlingbody(url: String, titleElements: Elements): Array<Any> {
        val jsonObject = parser.parse(titleElements.attr("data-params")) as JSONObject
        val encMenuSeq = jsonObject["encMenuSeq"] as String
        val encMenuBoardSeq = jsonObject["encMenuBoardSeq"] as String

        val index = url.indexOf("menu") + 4
        val articleURL = url.substring(0, index) + "/board/info" + url.substring(index) +
                "?scrtWrtiYn=false&encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq

        val articleDocument = Jsoup.connect(articleURL).get()

        val dateString = articleDocument.getElementsByClass("tblw_date").select("span")[0].text().substring(5)
        val regdate = LocalDateTime.parse(dateString, dateTimeFormatter)

        val type = articleDocument.getElementsByClass("wri_area colum20").text().replace("게시판명 ", "")

        val html = articleDocument.getElementsByClass("tbody").select("ul").select("li p:not([style*='display:none'])")

        var body = ""
        for (i in html) {
            body += i.text()
            body += "\n"
        }

        var img = ""
        for (i in html.select("img")) {
            val temp = i.attr("abs:src")
            // img tag inline data
            if (temp.substring(0, 30).contains("data:image")) {
                i.remove()
                continue
            } else {
                img += "$temp;"
            }
        }

        return arrayOf(regdate, type, body, img, html.toString())
    }

    fun findById(id: Long): Notice {
        return noticeRepo.findById(id).orElseThrow {
            IllegalArgumentException(
                "not found: $id"
            )
        }
    }

    fun findAll(): List<Notice> {
        return noticeRepo.findAll()
    }

    @Transactional
    fun search(major: String?, type: String?, keyword: String?, pageable: Pageable): Page<Notice> {
        var major = major
        var type = type
        var keyword = keyword
        major = if ((major == null)) "" else major
        type = if ((type == null)) "" else type
        keyword = if ((keyword == null)) "" else keyword

        if (major == "행사/안내") {
            return noticeRepo.findByMajorContainingAndTypeContainingAndTitleContaining(major, type, keyword, pageable)
        }
        return noticeRepo.findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(major, type, keyword, pageable)
    }


    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    fun findTopView(pageable: Pageable?): List<Notice> {
        //한달 동안
        val notices = noticeRepo.findByDescWhereByRegDate(LocalDate.now().minusDays(30), pageable)
        return notices
    }

    fun findTopLike(major: String?, pageable: Pageable?): List<Notice> {
        var major = major
        major = if ((major == null)) "" else major
        val topNotices = noticeRepo.findByMajorContaining(major, pageable)

        val result: MutableList<Notice> = ArrayList()

        if (topNotices != null && !topNotices.isEmpty()) {
            for (notice in topNotices) {
                if (notice.likeCount > 0) {
                    result.add(notice)
                }
            }
        }

        return result
    }

    fun count(): Long {
        return noticeRepo.count()
    }
}