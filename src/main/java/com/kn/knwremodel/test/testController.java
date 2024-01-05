package com.kn.knwremodel.test;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kn.knwremodel.College.CollegeService;
import com.kn.knwremodel.Comment.Comment;
import com.kn.knwremodel.Comment.CommentDTO;
import com.kn.knwremodel.Comment.CommentService;
import com.kn.knwremodel.Haksa.Haksa;
import com.kn.knwremodel.Haksa.HaksaService;
import com.kn.knwremodel.Like.LikeService;
import com.kn.knwremodel.Notice.Notice;
import com.kn.knwremodel.Notice.NoticeController;
import com.kn.knwremodel.Notice.NoticeDTO;
import com.kn.knwremodel.Notice.NoticeService;
import com.kn.knwremodel.Notice.NoticeDTO.responsebody;
import com.kn.knwremodel.User.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class testController {
    private final HaksaService haksaS;
    private final NoticeService noticeS;
    private final CollegeService collegeS;
    private final HttpSession httpSession;
    private final NoticeController noticeC;
    private final LikeService likeS;
    private final CommentService commentS;

    @GetMapping(value = {"/"})
    public String test(String keyword,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) throws IOException {
        UserDTO.Session currentuserDTO = (UserDTO.Session) httpSession.getAttribute("user");

        if (currentuserDTO != null) {
            model.addAttribute("currentuser", currentuserDTO);
        }

        model.addAttribute("majorlist", collegeS.findAllMajor());
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

    @GetMapping(value = "/haksa")
    public String test111(Model model) throws IOException {
        List<Haksa> haksas = haksaS.findAll();
        model.addAttribute("test", haksas);

        return "haksa";
    }

    @GetMapping("/top5View")
    public String getTop5View(Model model) {
        List<Notice> topNotices = noticeS.findTopView(PageRequest.of(0, 5, Sort.Direction.DESC, "view"));

        model.addAttribute("topNotices", topNotices);
        return "top5View";
    }

    @GetMapping("/myPage")
    public String showLikedNoticesAndComments(Model model) {
        UserDTO.Session currentuserDTO = (UserDTO.Session) httpSession.getAttribute("user");

        if (currentuserDTO != null) {
            model.addAttribute("currentuser", currentuserDTO);
        }

        List<Notice> likedNotices = likeS.getLikedNotices(currentuserDTO);
        model.addAttribute("likedNotices", likedNotices);

        UserDTO.Session currentUserDTO = (UserDTO.Session) httpSession.getAttribute("user");
        List<Comment> userComments = commentS.getCommentsByUser(currentUserDTO.getId());
        model.addAttribute("comments", userComments);

        return "myPage";
    }

    @GetMapping("/scholarshipwiki")
    public String scholarshipwiki(Model model) {

        return "scholarshipwiki";
    }
} 