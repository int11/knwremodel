package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.haksa;
import com.kn.knwremodel.service.CommentService;
import com.kn.knwremodel.service.LikeService;
import com.kn.knwremodel.service.NoticeService;
import com.kn.knwremodel.service.haksaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class testController {
    private final haksaService haksaS;
    private final NoticeService noticeS;
    private final HttpSession httpSession;
    private final CommentService commentService;
    private final LikeService likeService;
      
    @GetMapping(value="/")
    public String test(Model model) throws IOException{
       UserDTO.Session user = (UserDTO.Session)httpSession.getAttribute("user");

        if(user != null) {
            // 수정할 예정
            commentService.setLoginUserId(user.getId());
            likeService.setLoginUserId(user.getId());
            model.addAttribute("currentuser", user);
        }
        else {
            // 수정할 예정
            commentService.setLoginUserId(-1L);
            likeService.setLoginUserId(-1L);
        }

        List<Notice> notices = noticeS.findAll();        
        model.addAttribute("test", notices);
        return "index";
    }

    @GetMapping(value="/as")
    public String test111(Model model) throws IOException{
        haksaS.crawlAndSaveData();
        List<haksa> haksas = haksaS.findAll();
        model.addAttribute("test", haksas);

        return "testhaksa";
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
    public String logout() {
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/read/{id}")
    public String findNotice(@PathVariable Long id, Model model) {
        Notice notice = noticeS.findById(id);
        List<Comment> comments = notice.getComments();
        boolean isCheckedLike;

        model.addAttribute("test", notice);

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        isCheckedLike = likeService.checkedLike(id);
        model.addAttribute("like", isCheckedLike);
        return "index2";
    }

    @GetMapping("/requestNotice/{major}/{type}")
    public String requestNotice(@PathVariable String major, @PathVariable String type, Model model) {
        List<Notice> test = noticeS.findByMajorAndType(major, type, 10L, 1L);
        model.addAttribute("test", test);
        return "index";
    }
}