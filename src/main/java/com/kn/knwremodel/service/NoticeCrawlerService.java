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
    private int maxPage = 2; //크롤링할 공지사항 페이지의 수
    @Transactional
    public void updata() {
        List<Notice> notices = noticeRepo.findAll();
        JSONParser parser = new JSONParser();
        
        try {
            if (notices.isEmpty() || !notices.get(notices.size()-1).getId().equals(first_Notice_id())) {
                loopout:
                for (int page = 1; page <= maxPage; page++) {
                    Document document = Jsoup.connect(url + page).get();
                    Elements contents = document.getElementsByClass("tbody").select("ul");

                    for (Element content : contents) {
                        String Column_number = content.select("li").first().text();
                        
                        if (Column_number.equals("필독") || Column_number.equals("공지")) {
                            continue;
                        }

                        Long id = Long.parseLong(Column_number);
                        Elements titlElements = content.select("a");

                        if (!noticeRepo.findById(id).isEmpty()){
                            break loopout;
                        }

                        //게시물 내용, 사진 크롤링
                        JSONObject jsonObject = (JSONObject)parser.parse(titlElements.attr("data-params"));
                        String encMenuSeq = (String) jsonObject.get("encMenuSeq");
                        String encMenuBoardSeq = (String) jsonObject.get("encMenuBoardSeq");

                        String articleURL = "https://web.kangnam.ac.kr/menu/board/info/f19069e6134f8f8aa7f689a4a675e66f.do?scrtWrtiYn=false&encMenuSeq="
                                + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                        Document articleDocument = Jsoup.connect(articleURL).get();
                        Elements articleContents = articleDocument.getElementsByClass("tbody").select("ul");
                        

                        String post = articleContents.select("li p:gt(1)").text();

                        String img = "";
                        for (Element i : articleContents.select("img")) {
                            String temp = i.attr("abs:src");
                            // img tag inline data
                            if (temp.substring(0, 30).contains("data:image")){
                                continue;
                            }else{
                                img += temp + ";";
                            }
                        }
                        
                        notices.add(new Notice(id,
                                titlElements.text(),
                                content.select("li.ali").text(),
                                content.select("li.sliceDot6").text(),
                                content.select("li.sliceDot6").next().text(),
                                content.select("li.sliceDot6").next().next().text(),
                                post,
                                img));
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
        Document document = Jsoup.connect(url + 1).get();
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
    //분야별 키워드 분류 테스트 코드
    @Transactional
    public List<Notice> findBytype(String type) {
        List<Notice> notices = noticeRepo.findByTypeContaining(type);
        return notices;
    }
}
