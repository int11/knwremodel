package com.kn.knwremodel.Crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;


@Service
public class MainCrawler {
    public void getNewsDatas(){
        Connection doc = Jsoup.connect("https://en.wikipedia.org/");
    }
}
