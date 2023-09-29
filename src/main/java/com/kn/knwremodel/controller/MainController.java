package com.kn.knwremodel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.NoticeRepository;
import com.kn.knwremodel.service.NoticeCrawlerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final NoticeRepository noticeRepo;
    private final NoticeCrawlerService noticeCrawler;

    @GetMapping(value="/")
    public String test(Model model) throws IOException{
        noticeCrawler.updata();
        //testtest
        List<Notice> notices = noticeRepo.findAll();

        model.addAttribute("test", notices);
        return "index";
    }

}