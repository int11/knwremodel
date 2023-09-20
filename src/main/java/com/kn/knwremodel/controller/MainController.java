package com.kn.knwremodel.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kn.knwremodel.Crawler.MainCrawler;

@Controller
public class MainController {
    private final MainCrawler crawler;

    public MainController(MainCrawler Crawler) {
        this.crawler = Crawler;
    }

    @GetMapping(value="/")
    public String news(Model model) throws IOException{
        return "index";
    }
}