package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.NoticeDTO;
import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Keyword;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.Haksa;
import com.kn.knwremodel.service.HaksaService;
import com.kn.knwremodel.service.CollegeService;
import com.kn.knwremodel.service.CommentService;
import com.kn.knwremodel.service.LikeService;
import com.kn.knwremodel.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class testController {
    private final HaksaService haksaS;
    private final NoticeService noticeS;
    private final CollegeService collegeS;
    private final HttpSession httpSession;
    private final LikeService likeService;

    @GetMapping(value={"/"})
    public String test(@RequestParam(defaultValue = "1") Long page, @RequestParam(required = false) String major,
                       @RequestParam(required = false) String type, @RequestParam(required = false) String keyword,
                       Model model) throws IOException{
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");

        if(currentuserDTO != null) {
            model.addAttribute("currentuser", currentuserDTO);
        }
        if (major == ""){
            major = null;
        }
        if (keyword == ""){
            keyword = null;
        }
        Long noticesperPage = 15L;
        List<Notice> notices = noticeS.findByMajorAndTypeOrKeyword(major, type, keyword);
        List<Keyword> keywords = noticeS.findTop5ByKeyword(keyword);

        Long e = Math.min(noticesperPage * page, notices.size());
        
        model.addAttribute("majorlist",  collegeS.findAllMajor());
        model.addAttribute("maxpage", notices.size()/noticesperPage + 1);
        model.addAttribute("notices", notices.subList((int) (noticesperPage * (page - 1)), e.intValue()));
        model.addAttribute("keywords", keywords);

        return "index";
    }

    @GetMapping(value="/as")
    public String test111(Model model) throws IOException{
        List<Haksa> haksas = haksaS.findAll();
        model.addAttribute("test", haksas);

        return "testhaksa";
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

    @GetMapping("/read/{noticeid}")
    public String findNotice(@PathVariable Long noticeid, Model model) {
        Notice notice = noticeS.findById(noticeid);
        List<Comment> comments = notice.getComments();
        boolean isCheckedLike;

        model.addAttribute("likesize", notice.getLikes().size());
        model.addAttribute("notice", notice);

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        isCheckedLike = likeService.checkedLike(noticeid);


        model.addAttribute("like", isCheckedLike);
        return "index2";
    }

    @GetMapping("/top5View")
    public String getTop5View(Model model, @PageableDefault(size = 5, sort = "view", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Notice> topNotices = noticeS.findTop5ByView(pageable);

        topNotices.sort(Comparator.comparing(Notice::getView).reversed()
                .thenComparing(Notice::getCreateDate, Comparator.reverseOrder()));

        model.addAttribute("topNotices", topNotices);
        return "top5View";
    }
}