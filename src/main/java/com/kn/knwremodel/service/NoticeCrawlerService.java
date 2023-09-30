package com.kn.knwremodel.service;

import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeCrawlerService {
    private final NoticeRepository noticeRepo;

    private String url = "https://web.kangnam.ac.kr/menu/f19069e6134f8f8aa7f689a4a675e66f.do?paginationInfo.currentPageNo=";
    private int maxPage = 5; //크롤링할 공지사항 페이지의 수


    @Transactional
    public void updata() {
        List<Notice> notices = noticeRepo.findAll();

        try {
            if (notices.isEmpty() || !notices.get(0).getBoard_id().equals(first_Notice_id())) {
                for (int page = 1; page <= maxPage; page++) {
                    Connection conn = Jsoup.connect(url + page);
                    Document document = conn.get();
                    Elements contents = document.getElementsByClass("tbody").select("ul");

                    for (Element content : contents) {

                        if (content.select("li").first().text().equals("필독") ||
                                content.select("li").first().text().equals("공지")) {
                            continue;
                        }

                        //게시물 내용, 사진 크롤링
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(content.select("a").attr("data-params"));
                        JSONObject jsonObject = (JSONObject) obj;
                        String encMenuSeq = (String) jsonObject.get("encMenuSeq");
                        String encMenuBoardSeq = (String) jsonObject.get("encMenuBoardSeq");

                        String articleURL = "https://web.kangnam.ac.kr/menu/board/info/f19069e6134f8f8aa7f689a4a675e66f.do?scrtWrtiYn=false&encMenuSeq="
                                + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                        Connection articleConn = Jsoup.connect(articleURL);
                        Document articleDocument = articleConn.get();
                        Elements articleContents = articleDocument.getElementsByClass("tbody").select("ul");

                        String post = articleContents.select("li p").text();
                        String img = articleContents.select("img").attr("abs:src");


                        // 이미지가 없거나 불안정한 경로일 경우 null 반환
                        if (img.length() > 256 || img.isEmpty())
                            img = null;

                        notices.add(new Notice(Long.parseLong(content.select("li").first().text()),
                                content.select("a").text(),
                                content.select("li.ali").text(),
                                content.select("li.sliceDot6").text(),
                                content.select("li.sliceDot6").next().text(),
                                content.select("li.sliceDot6").next().next().text(),
                                post,
                                img)

                        );
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

    private Long first_Notice_id() throws IOException {
        Connection conn = Jsoup.connect(url + 1);
        Document document = conn.get();
        Elements contents = document.getElementsByClass("tbody").select("ul");
        Long board_id = 0L;

        for (Element content : contents) {
            if (content.select("li").first().text().equals("필독") ||
                    content.select("li").first().text().equals("공지")) {
                continue;
            }

            board_id = Long.parseLong(content.select("li").first().text());
            break;
        }

        if (board_id == 0) {
            throw new IOException();
        } else {
            return board_id;
        }
    }
}
