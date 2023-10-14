package com.kn.knwremodel.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Comment;

import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.service.NoticeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final NoticeService noticeS;
    private final HttpSession httpSession;
      
    @GetMapping(value="/")
    public String test(Model model) throws IOException{
       UserDTO.Session user = (UserDTO.Session)httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("currentuser", user);
        }
      
        List<Notice> notices = noticeS.findAll();        
        model.addAttribute("test", notices);
        return "index";
    }


    @GetMapping("/{Major}")
    public String searchNotice(@PathVariable String Major, Model model) {
        List<Notice> notices = noticeS.findByMajorContaining(Major);
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

    @GetMapping("/read/{id}")
    public String findNotice(@PathVariable Long id, Model model) {
        Notice notice = noticeS.findById(id);
        List<Comment> comments = notice.getComments();

        model.addAttribute("test", notice);

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }
        return "index2";
    }

    @GetMapping("/requestNotice/{major}/{type}")
    public String requestNotice(@PathVariable String major, @PathVariable String type, Model model) {
        List<Notice> test = noticeS.findByMajorAndType(major, type, 10L, 1L);
        model.addAttribute("test", test);
        return "index";
    }
}