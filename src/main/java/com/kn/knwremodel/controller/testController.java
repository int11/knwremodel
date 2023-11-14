package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.CommentDTO;
import com.kn.knwremodel.dto.NoticeDTO;
import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.dto.pageDTO;
import com.kn.knwremodel.dto.NoticeDTO.responsebody;
import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Keyword;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.Haksa;
import com.kn.knwremodel.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class testController {
    private final HaksaService haksaS;
    private final NoticeService noticeS;
    private final CollegeService collegeS;
    private final HttpSession httpSession;
    private final NoticeController noticeC;
    private final LikeService likeS;
    private final KeywordService keywordS;


    @GetMapping(value={"/"})
    public String test(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "20") Long perPage,
                       @RequestParam(required = false) String major, @RequestParam(required = false) String type,
                       @RequestParam(required = false) String keyword, HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) throws IOException {
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");

        if(currentuserDTO != null) {
            model.addAttribute("currentuser", currentuserDTO);
        }

        ResponseEntity result = noticeC.requestPage(new NoticeDTO.requestPage(major, type, keyword, page, perPage));

        List<Keyword> keywords = keywordS.findTop5ByKeyword(keyword, request);
        List<String> recentlyKeywords = keywordS.recentKeywords(keyword, request, response);


        model.addAttribute("majorlist",  collegeS.findAllMajor());
        model.addAttribute("page", result.getBody());
        model.addAttribute("keywords", keywords);
        model.addAttribute("recentlyKeywords", recentlyKeywords);
        return "mainpage";
    }

    @GetMapping("/read/{noticeid}")
    public String findNotice(@PathVariable Long noticeid, Model model) {
        ResponseEntity result = noticeC.requestbody(new NoticeDTO.requestbody(noticeid));
        NoticeDTO.responsebody notice = (responsebody) result.getBody();
        model.addAttribute("notice", notice);

        List<CommentDTO.Comment> comments = notice.getComments();

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        return "noticebody";
    }

    @GetMapping(value="/haksa")
    public String test111(Model model) throws IOException{
        List<Haksa> haksas = haksaS.findAll();
        model.addAttribute("test", haksas);

        return "haksa";
    }

    @GetMapping("/mainlogin")
    public String mainlogin(Model model) {

        return "mainlogin";
    }

    @GetMapping("/logout")
    public String logout() {
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/top5View")
    public String getTop5View(Model model, @PageableDefault(size = 5, sort = "view", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Notice> topNotices = noticeS.findTop5ByView(pageable);

        topNotices.sort(Comparator.comparing(Notice::getView).reversed()
                .thenComparing(Notice::getCreateDate, Comparator.reverseOrder()));

        model.addAttribute("topNotices", topNotices);
        return "top5View";
    }

    @GetMapping("/likePage")
    public String showLikedNotices(Model model) {
        List<Notice> likedNotices = likeS.getLikedNotices();
        model.addAttribute("likedNotices", likedNotices);
        return "likePage";
    }
}