package com.kn.knwremodel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeCrawlerService {
    private final NoticeRepository test0;
    private final NoticeRepository test1;
    private final NoticeRepository test2;
    private final NoticeRepository noticeRepo;

    private String url = "https://web.kangnam.ac.kr/menu/f19069e6134f8f8aa7f689a4a675e66f.do?paginationInfo.currentPageNo=";
    private int maxpage = 5; //크롤링할 공지사항 페이지의 수
    

    @Transactional
    public void updata(){
        List<Notice> notices = noticeRepo.findAll();

        try {
            if (notices.isEmpty() || !notices.get(0).getBoard_id().equals(first_Notice_id())){
                for (int page = 1; page <= maxpage; page++) {
                    Connection conn = Jsoup.connect(url + page);
                    Document document = conn.get();
                    Elements contents = document.getElementsByClass("tbody").select("ul");

                    for (Element content : contents) {

                        if (content.select("li").first().text().equals("필독")) {
                            continue;
                        }

                        notices.add(new Notice(Long.parseLong(content.select("li").first().text()),
                                               content.select("a").text(),
                                               content.select("li.ali").text(),
                                               content.select("li.sliceDot6").text(),
                                               content.select("li.sliceDot6").next().text(),
                                               content.select("li.sliceDot6").next().next().text()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        noticeRepo.saveAll(notices);
    }

    private Long first_Notice_id() throws IOException {
        Connection conn = Jsoup.connect(url + 1);
        Document document = conn.get();
        Elements contents = document.getElementsByClass("tbody").select("ul");
        Long board_id = 0L;

        for (Element content : contents) {
            if (content.select("li").first().text().equals("필독")) {
                continue;
            }
            
            board_id = Long.parseLong(content.select("li").first().text());
            break;
        }
        
        if (board_id == 0){
            throw new IOException();
        }
        else{
            return board_id;
        }
    }
}
