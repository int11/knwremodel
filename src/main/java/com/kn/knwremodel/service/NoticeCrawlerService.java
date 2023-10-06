package com.kn.knwremodel.service;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kn.knwremodel.entity.College;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.CollegeRepository;
import com.kn.knwremodel.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class NoticeCrawlerService {
    private final NoticeRepository noticeRepo;
    private final CollegeRepository CollegeRepo;
    private int maxPage = 1; //크롤링할 공지사항 페이지의 수
    @Transactional
    public void updata() {
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
                            String Column_number = content.select("li").first().text();

                            if (Column_number.equals("필독") || Column_number.equals("공지")) {
                                continue;
                            }

                            int id = Integer.parseInt(Column_number);
                            Elements titlElements = content.select("a");

                            if (noticeRepo.existsByBoardId(id)){
                                break loopout;
                            }

                            //게시물 내용, 사진 크롤링
                            JSONObject jsonObject = (JSONObject)parser.parse(titlElements.attr("data-params"));
                            String encMenuSeq = (String) jsonObject.get("encMenuSeq");
                            String encMenuBoardSeq = (String) jsonObject.get("encMenuBoardSeq");

                            int index = e.getUrl().indexOf("menu") + 4;
                            String articleURL = e.getUrl().substring(0, index) + "/board/info" + e.getUrl().substring(index) +
                                    "?scrtWrtiYn=false&encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;


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
                                    e.getMajor(),
                                    content.select("li.sliceDot6").text(),
                                    content.select("li.sliceDot6").next().text(),
                                    Integer.parseInt(content.select("li.sliceDot6").next().next().text().replace(",", "")),
                                    post,
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

    private int first_Notice_id(String url) throws IOException {
        Document document = Jsoup.connect(url + "?paginationInfo.currentPageNo=" + 1).get();
        Elements contents = document.getElementsByClass("tbody").select("ul");
        int boardId = 0;

        for (Element content : contents) {
            if (content.select("li").first().text().equals("필독") ||
                    content.select("li").first().text().equals("공지")) {
                continue;
            }

            boardId = Integer.parseInt(content.select("li").first().text());
            break;
        }

        if (boardId == 0) {
            throw new IOException();
        } else {
            return boardId;
        }
    }
    public Notice findById(Long id) {
        return noticeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }
}