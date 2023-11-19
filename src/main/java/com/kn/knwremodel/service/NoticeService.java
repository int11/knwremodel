package com.kn.knwremodel.service;

import com.kn.knwremodel.entity.College;
import com.kn.knwremodel.entity.Keyword;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.CollegeRepository;
import com.kn.knwremodel.repository.KeywordRepository;
import com.kn.knwremodel.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepo;
    private final CollegeRepository CollegeRepo;

    private int maxPage = 1; //크롤링할 공지사항 페이지의 수

    @Setter
    private LocalDate nowDate;

    @Transactional
    public void update() {
        List<Notice> notices = noticeRepo.findAll();
        JSONParser parser = new JSONParser();

        try {
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
                            Elements titleElements = content.select("a");

                            if (noticeRepo.existsByBoardId(id)) {
                                break loopout;
                            }

                            //게시물 내용, 사진 크롤링
                            JSONObject jsonObject = (JSONObject) parser.parse(titleElements.attr("data-params"));
                            String encMenuSeq = (String) jsonObject.get("encMenuSeq");
                            String encMenuBoardSeq = (String) jsonObject.get("encMenuBoardSeq");

                            int index = e.getUrl().indexOf("menu") + 4;
                            String articleURL = e.getUrl().substring(0, index) + "/board/info" + e.getUrl().substring(index) +
                                    "?scrtWrtiYn=false&encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;


                            Document articleDocument = Jsoup.connect(articleURL).get();
                            Elements articleContents = articleDocument.getElementsByClass("tbody").select("ul");

                            String body = "";
                            for (Element i : articleContents.select("li p:not([style*='display:none'])")) {
                                String temp = i.text();
                                if (temp != "") {
                                    body += temp;
                                    body += "\n";
                                }

                            }
                            // html 통째로 긁기 프론트엔드랑 회의 필요
                            // String post = articleContents.select("li p:not([style*='display:none'])").toString();


                            String img = "";
                            for (Element i : articleContents.select("img")) {
                                String temp = i.attr("abs:src");
                                // img tag inline data
                                if (temp.substring(0, 30).contains("data:image")) {
                                    continue;
                                } else {
                                    img += temp + ";";
                                }
                            }

                            String regDate = content.select("li.sliceDot6").next().text();
                            DateTimeFormatter JEFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                            LocalDate localDate = LocalDate.parse("20" + regDate, JEFormatter);


                            notices.add(new Notice(id,
                                    titleElements.text(),
                                    content.select("li.ali").text(),
                                    e.getMajor(),
                                    content.select("li.sliceDot6").text(),
                                    localDate,
                                    Long.parseLong(content.select("li.sliceDot6").next().next().text().replace(",", "")),
                                    body,
                                    img));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        noticeRepo.saveAll(notices);
    }

    private Long first_Notice_id(String url) throws IOException {
        Document document = Jsoup.connect(url + "?paginationInfo.currentPageNo=" + 1).get();
        Elements contents = document.getElementsByClass("tbody").select("ul");
        Long boardId = null;

        for (Element content : contents) {
            if (content.select("li").first().text().equals("필독") ||
                    content.select("li").first().text().equals("공지")) {
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

        if (major.equals("행사/안내"))
            return noticeRepo.findByMajorContainingAndTypeContainingAndTitleContaining(major, type, keyword);

        return noticeRepo.findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(major, type, keyword);
    }


    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<Notice> findTop5ByView(Pageable pageable) {
        //한달 동안
        List<Notice> notices = noticeRepo.findByOrderByViewDescWhereByRegDate(nowDate.minusDays(30), nowDate, pageable);
        return notices;
    }

    public List<Notice> findTopLikesByMajor(String major) {
        List<Notice> topNotices = noticeRepo.findTop3ByMajorOrderByLikeCountDesc(major);

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