package injea.knwremodel.Notice

import injea.knwremodel.College.CollegeRepository
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

@Service
class NoticeService(private val noticeRepo: NoticeRepository, private val CollegeRepo: CollegeRepository) {
    private val maxPage = 1 //크롤링할 공지사항 페이지의 수
    private val pattern: Pattern = Pattern.compile("\\((\\d+)\\)")
    private val parser = JSONParser()

    fun updateAll() {
        try {
            update_Event()
            update_Notice()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Transactional
    @Throws(Exception::class)
    fun update_Notice() {
        val notices = noticeRepo.findAll()

        for (e in CollegeRepo.findAll()) {
            if (!noticeRepo.existsByMajor(e.major) || noticeRepo.findMaxBoardIdByMajor(e.major)
                    .toLong() != first_Notice_id(e.url)
            ) {
                loopout@ for (page in 1..maxPage) {
                    val document = Jsoup.connect(e.url + "?paginationInfo.currentPageNo=" + page).get()
                    val contents = document.getElementsByClass("tbody").select("ul")

                    for (content in contents) {
                        val columnNumber = content.select("li").first()!!.text()

                        if (columnNumber == "필독" || columnNumber == "공지") {
                            continue
                        }

                        val boardId = columnNumber.toLong()

                        if (noticeRepo.existsByBoardId(boardId)) {
                            break@loopout
                        }

                        val titleElements = content.select("a")

                        //regdate
                        val regDate = content.select("li.sliceDot6").next().text()
                        val localDate = StringToLocaldate(regDate)
                        //게시물 타입, 내용, 사진 크롤링
                        val body_li = crawlingbody(e.url, titleElements)


                        notices.add(
                            Notice(
                                boardId,
                                titleElements.text(),
                                body_li[0],
                                e.major,
                                content.select("li.sliceDot6").text(),
                                localDate,
                                content.select("li.sliceDot6").next().next().text().replace(",", "").toLong(),
                                body_li[1], body_li[2], body_li[3]
                            )
                        )
                    }

                    if (isLastpage(document, page)) {
                        break
                    }
                }
            }
            noticeRepo.saveAll(notices)
        }
    }


    @Transactional
    @Throws(Exception::class)
    fun update_Event() {
        val url = "https://web.kangnam.ac.kr/menu/e4058249224f49ab163131ce104214fb.do"
        val eventNotice = noticeRepo.findByMajorAndRegdate("행사/안내", LocalDate.now())
        val events: MutableList<Notice> = ArrayList()
        var update = true

        if (!eventNotice.isEmpty()) for (e in eventNotice) if (e.title == first_Notice_title(url)) {
            update = false
            break
        }

        // 1. DB가 비어있거나
        // 2.행사/안내의 첫번째 게시물 제목이 eventNotice 안에 존재하지 않을 경우
        if (eventNotice.isEmpty() || update) {
            loopout@ for (page in 1..maxPage) {
                val document = Jsoup.connect("$url?paginationInfo.currentPageNo=$page").get()
                val contents = document.getElementsByClass("tbody").select("ul")
                for (content in contents) {
                    val titleElements = content.select("a")
                    // 3. DB가 비어있지 않지만 행사/안내의 첫번째 게시물 제목과 첫번째 DB의 title 값이 다를 경우
                    if (!eventNotice.isEmpty()) for (e in eventNotice) if (e.title == titleElements.attr("title")) break@loopout

                    //regdate
                    val regDate = content.select("li dd span").next().first()!!.text().replace("등록일 ", "")
                    val localDate = StringToLocaldate(regDate)
                    //게시물 타입, 내용, 사진 크롤링
                    val body_li = crawlingbody(url, titleElements)

                    events.add(
                        Notice(
                            null,  //최근 게시물 출력 순서를 조절하기 위함
                            titleElements.text(),
                            body_li[0],
                            "행사/안내",
                            content.select("li dd span").first()!!.text().replace("작성자 ", ""),
                            localDate,
                            content.select("li dd span")
                                .next().next().text().replace("조회수 ", "").replace(",", "").toLong(),
                            body_li[1], body_li[2], body_li[3]
                        )
                    )
                }

                if (isLastpage(document, page)) {
                    break
                }
            }
            noticeRepo.saveAll(events)
        }
    }

    private fun isLastpage(document: Document, currentpage: Int): Boolean {
        val lp = document.getElementsByClass("pagination create_mob_pagination").select("a").last()!!.attr("onclick")

        val matcher = pattern.matcher(lp)
        if (matcher.find()) {
            val lastpage = matcher.group(1).toLong()
            if (currentpage >= lastpage) {
                return true
            }
        }
        return false
    }

    private fun StringToLocaldate(regDate: String): LocalDate {
        val JEFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return LocalDate.parse("20$regDate", JEFormatter)
    }

    @Throws(IOException::class)
    private fun first_Notice_title(url: String): String {
        val document = Jsoup.connect("$url?paginationInfo.currentPageNo=1").get()
        val contents = document.getElementsByClass("tbody").select("ul")
        var boardTitle: String? = null

        for (content in contents) {
            boardTitle = content.select("a").attr("title")
            break
        }

        if (boardTitle == null) {
            throw IOException()
        } else {
            return boardTitle
        }
    }

    @Throws(IOException::class)
    private fun first_Notice_id(url: String): Long {
        val document = Jsoup.connect("$url?paginationInfo.currentPageNo=1").get()
        val contents = document.getElementsByClass("tbody").select("ul")
        var boardId: Long? = null

        for (content in contents) {
            val columnNumber = content.select("li").first()!!.text()
            if (columnNumber == "필독" || columnNumber == "공지") {
                continue
            }

            boardId = content.select("li").first()!!.text().toLong()
            break
        }

        if (boardId == null) {
            throw IOException()
        } else {
            return boardId
        }
    }

    @Throws(Exception::class)
    private fun crawlingbody(url: String, titleElements: Elements): Array<String> {
        val jsonObject = parser.parse(titleElements.attr("data-params")) as JSONObject
        val encMenuSeq = jsonObject["encMenuSeq"] as String
        val encMenuBoardSeq = jsonObject["encMenuBoardSeq"] as String

        val index = url.indexOf("menu") + 4
        val articleURL = url.substring(0, index) + "/board/info" + url.substring(index) +
                "?scrtWrtiYn=false&encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq

        val articleDocument = Jsoup.connect(articleURL).get()

        val type = articleDocument.getElementsByClass("wri_area colum20").text().replace("게시판명 ", "")

        val html = articleDocument.getElementsByClass("tbody").select("ul").select("li p:not([style*='display:none'])")

        var body = ""
        for (i in html) {
            val temp = i.text()
            if (temp !== "") {
                body += temp
                body += "\n"
            }
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

        return arrayOf(type, body, img, html.toString())
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
    fun search(major: String?, type: String?, keyword: String?): List<Notice> {
        var major = major
        var type = type
        var keyword = keyword
        major = if ((major == null)) "" else major
        type = if ((type == null)) "" else type
        keyword = if ((keyword == null)) "" else keyword

        if (major == "행사/안내") {
            val sort = Sort.by(Sort.Order.desc("regdate"), Sort.Order.desc("id"))
            return noticeRepo.findByMajorContainingAndTypeContainingAndTitleContaining(major, type, keyword, sort)
        }

        return noticeRepo.findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(major, type, keyword)
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