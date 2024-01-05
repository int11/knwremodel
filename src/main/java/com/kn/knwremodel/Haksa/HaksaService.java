package com.kn.knwremodel.Haksa;

import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Year;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HaksaService {
    @Autowired
    private HaksaRepository haksaRepo;
    @Autowired
    private HaksayearRepository haksayearRepo;

    public void update() {
        // 학사 데이터가 아예 존재하지않을때 or 크롤링한 학사 일정 년도수가 현재 년도수랑 다를떄
        if (haksaRepo.findAll().size() == 0 || haksayearRepo.findAll().get(0).getYear().equals(Year.now()) == false){
            haksaRepo.deleteAll();
            haksayearRepo.save(new Haksayear(1L, Year.now()));
            Document docu = null;
            final String url = "https://web.kangnam.ac.kr/menu/02be162adc07170ec7ee034097d627e9.do";
            try {
                docu = Jsoup.connect(url).get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (docu != null) {

                Elements dateElements1 = docu.select("div.tbl.typeA.calendal_list").select("tbody");
                Elements dateElements = dateElements1.select("th.text-center");
                Elements scheduleElements = dateElements1.select(".text-center.last");

                int size = Math.min(dateElements.size(), scheduleElements.size());

                for (int i = 0; i < size; i++) {
                    String date = dateElements.get(i).text();
                    String schedule = scheduleElements.get(i).text();
                    String[] dateParts = date.split("~");
                    String date_start = dateParts[0].trim();
                    String date_end = (dateParts.length == 2) ? dateParts[1].trim() : null;

                    Haksa event = new Haksa();

                    event.setSchedule(schedule);
                    event.setDateStart(date_start);
                    event.setDateEnd(date_end);

                    haksaRepo.save(event);
                }
            }
        }  
    }

    public List<Haksa> findAll() {
        return haksaRepo.findAll();
    }
}
