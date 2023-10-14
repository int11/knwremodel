package com.kn.knwremodel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.NoticeRepository;
import com.kn.knwremodel.service.NoticeCrawlerService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final NoticeRepository noticeRepo;
    private final NoticeCrawlerService noticeCrawler;
    private final HttpSession httpSession;

    @GetMapping(value="/")
    public String test(Model model) throws IOException{
        UserDTO.Session user = (UserDTO.Session)httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("currentuser", user);
        }

        noticeCrawler.updata();
        List<Notice> notices = noticeRepo.findAll();

        model.addAttribute("test", notices);
        return "index";
    }

    @GetMapping("/mainlogin")
    public String mainlogin(Model model) {

        return "mainlogin";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        httpSession.invalidate();
        return "index";
    }
}