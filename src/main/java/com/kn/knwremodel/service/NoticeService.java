package com.kn.knwremodel.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kn.knwremodel.entity.College;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.CollegeRepository;
import com.kn.knwremodel.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepo;
    private final CollegeRepository CollegeRepo;

    private int maxPage = 1; //크롤링할 공지사항 페이지의 수
    private final Pattern pattern = Pattern.compile("\\((\\d+)\\)");
    private final JSONParser parser = new JSONParser();

    public void updateAll(){
        try{
            update_Event();
            update_Notice();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void update_Notice() throws Exception{
        List<Notice> notices = noticeRepo.findAll();
    
        for (College e : CollegeRepo.findAll()) {
            if (!noticeRepo.existsByMajor(e.getMajor()) || !(noticeRepo.findMaxBoardIdByMajor(e.getMajor()) == first_Notice_id(e.getUrl()))) {
                loopout:

                for (int page = 1; page <= maxPage; page++) {
                    Document document = Jsoup.connect(e.getUrl() + "?paginationInfo.currentPageNo=" + page).get();
                    Elements contents = document.getElementsByClass("tbody").select("ul");

                    for (Element content : contents) {  
                        String columnNumber = content.select("li").first().text();

                        if (columnNumber.equals("필독") || columnNumber.equals("공지")) {
                            continue;
                        }

                        Long id = Long.parseLong(columnNumber);
                        
                        if (noticeRepo.existsByBoardId(id)) {
                            break loopout;
                        }

                        Elements titleElements = content.select("a");
                        
                        //regdate
                        String regDate = content.select("li.sliceDot6").next().text();
                        LocalDate localDate = StringToLocaldate(regDate);
                        //게시물 타입, 내용, 사진 크롤링
                        String[] body_li = crawlingbody(e.getUrl(), titleElements);


                        notices.add(new Notice(id,
                                titleElements.text(),
                                body_li[0],
                                e.getMajor(),
                                content.select("li.sliceDot6").text(),
                                localDate,
                                Long.parseLong(content.select("li.sliceDot6").next().next().text().replace(",", "")),
                                body_li[1], body_li[2], body_li[3]));
                    }

                    if (isLastpage(document, page)){
                        break;
                    }
                }
            }
            noticeRepo.saveAll(notices);
        }
    }

    

    @Transactional
    public void update_Event() throws Exception {
        String url = "https://web.kangnam.ac.kr/menu/e4058249224f49ab163131ce104214fb.do";
        List<Notice> eventNotice = noticeRepo.findByMajorAndRegdate("행사/안내", LocalDate.now());
        List<Notice> events = new ArrayList<>();
        Boolean update = true;

        if (!eventNotice.isEmpty())
            for (Notice e : eventNotice)
                if (e.getTitle().equals(first_Notice_title(url))){
                    update = false;
                    break;
                }

        // 1. DB가 비어있거나
        // 2.행사/안내의 첫번째 게시물 제목이 eventNotice 안에 존재하지 않을 경우
        if (eventNotice.isEmpty() || update) {
            loopout:
            for (int page = 1; page <= maxPage; page++) {
                Document document = Jsoup.connect(url + "?paginationInfo.currentPageNo=" + page).get();
                Elements contents = document.getElementsByClass("tbody").select("ul");
                for (Element content : contents) {
                    Elements titleElements = content.select("a");
                        // 3. DB가 비어있지 않지만 행사/안내의 첫번째 게시물 제목과 첫번째 DB의 title 값이 다를 경우
                    if (!eventNotice.isEmpty())
                        for (Notice e : eventNotice)
                            if (e.getTitle().equals(titleElements.attr("title")))
                                break loopout;
                    
                    //regdate
                    String regDate = content.select("li dd span").next().first().text().replace("등록일 ", "");
                    LocalDate localDate = StringToLocaldate(regDate);
                    //게시물 타입, 내용, 사진 크롤링
                    String[] body_li = crawlingbody(url, titleElements);

                    events.add(new Notice(
                            null, //최근 게시물 출력 순서를 조절하기 위함
                            titleElements.text(),
                            body_li[0],
                            "행사/안내",
                            content.select("li dd span").first().text().replace("작성자 ", ""),
                            localDate,
                            Long.parseLong(content.select("li dd span")
                                    .next().next().text().replace("조회수 ", "").replace(",", "")),
                            body_li[1], body_li[2], body_li[3]));
                }

                if (isLastpage(document, page)){
                        break;
                }
            }
            noticeRepo.saveAll(events);
        }
    }

    private boolean isLastpage(Document document, int currentpage){
        String lp = document.getElementsByClass("pagination create_mob_pagination").select("a").last().attr("onclick");

        Matcher matcher = pattern.matcher(lp);
        if (matcher.find()) {
            Long lastpage = Long.parseLong(matcher.group(1));
            if (currentpage >= lastpage){
                return true;
            }
        }
        return false;
    }

    private LocalDate StringToLocaldate(String regDate){
        DateTimeFormatter JEFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse("20" + regDate, JEFormatter);
    }

    private String first_Notice_title(String url) throws IOException {
        Document document = Jsoup.connect(url + "?paginationInfo.currentPageNo=" + 1).get();
        Elements contents = document.getElementsByClass("tbody").select("ul");
        String boardTitle = null;

        for (Element content : contents) {
            boardTitle = content.select("a").attr("title");
            break;
        }

        if (boardTitle == null) {
            throw new IOException();
        } else {
            return boardTitle;
        }
    }
    
    private Long first_Notice_id(String url) throws IOException {
        Document document = Jsoup.connect(url + "?paginationInfo.currentPageNo=" + 1).get();
        Elements contents = document.getElementsByClass("tbody").select("ul");
        Long boardId = null;

        for (Element content : contents) {
            String columnNumber = content.select("li").first().text();
            if (columnNumber.equals("필독") || columnNumber.equals("공지")) {
                continue;
            }

            boardId = Long.parseLong(content.select("li").first().text());
            break;
        }

        if (boardId == null) {
            throw new IOException();
        } else {
            return boardId;
        }
    }

    private String[] crawlingbody(String url, Elements titleElements) throws Exception{
        JSONObject jsonObject = (JSONObject) parser.parse(titleElements.attr("data-params"));
        String encMenuSeq = (String) jsonObject.get("encMenuSeq");
        String encMenuBoardSeq = (String) jsonObject.get("encMenuBoardSeq");

        int index = url.indexOf("menu") + 4;
        String articleURL = url.substring(0, index) + "/board/info" + url.substring(index) +
                "?scrtWrtiYn=false&encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

        Document articleDocument = Jsoup.connect(articleURL).get();

        String type = articleDocument.getElementsByClass("wri_area colum20").text().replace("게시판명 ", "");

        Elements html = articleDocument.getElementsByClass("tbody").select("ul").select("li p:not([style*='display:none'])");

        String body = "";
        for (Element i : html) {
            String temp = i.text();
            if (temp != "") {
                body += temp;
                body += "\n";
            }
        }

        String img = "";
        for (Element i : html.select("img")) {
            String temp = i.attr("abs:src");
            // img tag inline data
            if (temp.substring(0, 30).contains("data:image")) {
                i.remove();
                continue;
            } else {
                img += temp + ";";
            }
        }

        return new String[] {type, body, img, html.toString()};
    }

    public Notice findById(Long id) {
        return noticeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<Notice> findAll() {
        return noticeRepo.findAll();
    }

    @Transactional
    public List<Notice> search(String major, String type, String keyword) {
        major = (major == null) ? "" : major;
        type = (type == null) ? "" : type;
        keyword = (keyword == null) ? "" : keyword;

        if (major.equals("행사/안내")) {
            Sort sort = Sort.by(Sort.Order.desc("regdate"), Sort.Order.desc("id"));
            return noticeRepo.findByMajorContainingAndTypeContainingAndTitleContaining(major, type, keyword, sort);
        }

        return noticeRepo.findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(major, type, keyword);
    }


    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<Notice> findTopView(Pageable pageable) {
        //한달 동안
        List<Notice> notices = noticeRepo.findByDescWhereByRegDate(LocalDate.now().minusDays(30), pageable);
        return notices;
    }

    public List<Notice> findTopLike(String major, Pageable pageable) {
        major = (major == null) ? "" : major;
        List<Notice> topNotices = noticeRepo.findByMajorContaining(major, pageable);

        List<Notice> result = new ArrayList<>();

        if (topNotices != null && !topNotices.isEmpty()) {
            for (Notice notice : topNotices) {
                if (notice.getLikeCount() > 0) {
                    result.add(notice);
                }
            }
        }

        return result;
    }

    public Long count() {
        return noticeRepo.count();
    }
}