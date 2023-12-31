package com.kn.knwremodel.service;

import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final NoticeRepository noticeRepository;
    private final int maxPage = 2; //크롤링할 공지사항 페이지의 수
    private final String url = "https://web.kangnam.ac.kr/menu/e4058249224f49ab163131ce104214fb.do?paginationInfo.currentPageNo=";

    @Transactional
    public void update() throws IOException {
        List<Notice> eventNotice = noticeRepository.findByMajorAndRegdate("행사/안내", LocalDate.now());
        List<Notice> events = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Boolean update = true;

        if (!eventNotice.isEmpty())
            for (Notice e : eventNotice)
                if (e.getTitle().equals(first_Notice_title()))
                    update = false;

        // 1. DB가 비어있거나
        // 2.행사/안내의 첫번째 게시물 제목이 eventNotice 안에 존재하지 않을 경우
        if (eventNotice.isEmpty() || update) {
            try {
                loopout:
                for (int page = 1; page <= maxPage; page++) {
                    Document document = Jsoup.connect(url + page).get();
                    Elements contents = document.getElementsByClass("tbody").select("ul");
                    for (Element content : contents) {

                        JSONObject jsonObject = (JSONObject) parser.parse(content.select("a").attr("data-params"));
                        String encMenuSeq = (String) jsonObject.get("encMenuSeq");
                        String encMenuBoardSeq = (String) jsonObject.get("encMenuBoardSeq");

                        String articleURL = "https://web.kangnam.ac.kr/menu/board/info/e4058249224f49ab163131ce104214fb.do?" +
                                "scrtWrtiYn=false&encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                        Document articleDocument = Jsoup.connect(articleURL).get();
                        Elements articleContents = articleDocument.getElementsByClass("tbody").select("ul");

                        Elements tbody = articleContents.select("li p:not([style*='display:none'])");

                        String body = "";
                        for (Element i : articleContents.select("li p:not([style*='display:none'])")) {
                            String temp = i.text();
                            if (temp != "") {
                                body += temp;
                                body += "\n";
                            }

                        }

                        String img = "";
                        for (Element i : tbody.select("img")) {
                            String temp = i.attr("abs:src");
                            if (!temp.equals(""))   // img tag inline data
                                if (!temp.substring(0, 30).contains("data:image"))
                                    img += temp + ";";
                        }

                        String regDate = content.select("li dd span").next().first().text().replace("등록일 ", "");
                        DateTimeFormatter JEFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                        LocalDate localDate = LocalDate.parse("20" + regDate, JEFormatter);

                        // 3. DB가 비어있지 않지만 행사/안내의 첫번째 게시물 제목과 첫번째 DB의 title 값이 다를 경우
                        if (!eventNotice.isEmpty())
                            for (Notice e : eventNotice)
                                if (e.getTitle().equals(content.select("a").attr("title")))
                                    break loopout;

                        events.add(new Notice(
                                null, //최근 게시물 출력 순서를 조절하기 위함
                                content.select("a").attr("title"),
                                articleDocument.getElementsByClass("wri_area colum20").text().replace("게시판명 ", ""),
                                "행사/안내",
                                content.select("li dd span").first().text().replace("작성자 ", ""),
                                localDate,
                                Long.parseLong(content.select("li dd span")
                                        .next().next().text().replace("조회수 ", "").replace(",", "")),
                                body,
                                img,
                                tbody.toString()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            noticeRepository.saveAll(events);
        }
    }

    private String first_Notice_title() throws IOException {
        Document document = Jsoup.connect(url + 1).get();
        Elements contents = document.getElementsByClass("tbody").select("ul");
        String boardTitle = null;

        for (Element content : contents) {
            boardTitle = (content.select("a").attr("title"));
            break;
        }

        if (boardTitle == null) {
            throw new IOException();
        } else {
            return boardTitle;
        }
    }
}
