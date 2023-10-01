package com.kn.knwremodel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.haksa;
import com.kn.knwremodel.repository.NoticeRepository;
import com.kn.knwremodel.repository.haksaRepository;
import com.kn.knwremodel.service.NoticeCrawlerService;
import com.kn.knwremodel.service.haksaCrawlerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final NoticeRepository noticeRepo;
    private final NoticeCrawlerService noticeCrawler;
    private final haksaRepository haksaRe;
    private final haksaCrawlerService haksaCrawler;
    
    @GetMapping(value="/")
    public String test(Model model) throws IOException{
        noticeCrawler.updata();
        List<Notice> notices = noticeRepo.findAll();
        model.addAttribute("test", notices);

        return "index0";
    }

    @GetMapping(value="/as")
    public String test111(Model model) throws IOException{
        haksaCrawler.crawlAndSaveData();
        List<haksa> haksas = haksaRe.findAll();
        model.addAttribute("test", haksas);

        return "index1";
    }
}