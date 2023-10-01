package com.kn.knwremodel.service;

import com.kn.knwremodel.entity.haksa;
import com.kn.knwremodel.repository.haksaRepository;

import jakarta.el.ELManager;

import com.kn.knwremodel.repository.haksaRepository;
import jdk.jfr.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class haksaCrawlerService {
    @Autowired
    private haksaRepository haksaRepository;

    public void crawlAndSaveData() {
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

                haksa event = new haksa();
                event.setDate(date);
                event.setSchedule(schedule);
                haksaRepository.save(event);
                System.out.print("Date Elements: " + dateElements);
                System.out.println("Schedule Elements: " + scheduleElements);
                
            }
        }
    }

}
