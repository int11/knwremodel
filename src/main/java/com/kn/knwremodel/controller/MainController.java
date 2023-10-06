package com.kn.knwremodel.controller;

import java.io.IOException;
import java.util.List;

import com.kn.knwremodel.dto.CommentResponseDto;
import com.kn.knwremodel.dto.NoticeResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.NoticeRepository;
import com.kn.knwremodel.service.CollegeService;
import com.kn.knwremodel.service.NoticeCrawlerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final NoticeRepository noticeRepo;
    private final NoticeCrawlerService noticeCrawler;
    private final CollegeService collegeService;

    @GetMapping(value="/")
    public String test(Model model) throws IOException{
        List<Notice> notices = noticeRepo.findAll();
        model.addAttribute("test", notices);
        return "index";
    }

    @GetMapping("/{type}")
    public String searchNotice(@PathVariable String type, Model model) {
        List<Notice> search = noticeRepo.findByTypeContaining(type);
        model.addAttribute("test", search);
        return "index";
    }

    @GetMapping("/read/{id}")
    public String findNotice(@PathVariable Long id, Model model) {
        Notice notice = noticeCrawler.findById(id);
        NoticeResponseDto dto = new NoticeResponseDto(notice);
        List<CommentResponseDto> comments = dto.getComments();
        model.addAttribute("test", dto);

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }
        return "index2";
    }
}