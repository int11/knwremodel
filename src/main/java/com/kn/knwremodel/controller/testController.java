package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.haksa;
import com.kn.knwremodel.service.haksaService;
import com.kn.knwremodel.service.CommentService;
import com.kn.knwremodel.service.LikeService;
import com.kn.knwremodel.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class testController {
    private final haksaService haksaS;
    private final NoticeService noticeS;
    private final HttpSession httpSession;
    private final LikeService likeService;
      
    @GetMapping(value={"/"})
    public String test(@RequestParam(defaultValue = "1") Long page, @RequestParam(required = false) String major, @RequestParam(required = false) String type, Model model) throws IOException{
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");

        if(currentuserDTO != null) {
            model.addAttribute("currentuser", currentuserDTO);
        }
        List<Notice> notices = noticeS.findByMajorAndType(major, type, 15L, page);      
        model.addAttribute("notices", notices);
        return "index";
    }

    @GetMapping(value="/as")
    public String test111(Model model) throws IOException{
        List<haksa> haksas = haksaS.findAll();
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
}