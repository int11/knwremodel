package injea.knwremodel.test;

import java.io.IOException;
import java.util.List;

import injea.knwremodel.College.CollegeService;
import injea.knwremodel.Comment.Comment;
import injea.knwremodel.Comment.CommentService;
import injea.knwremodel.Haksa.Haksa;
import injea.knwremodel.Haksa.HaksaService;
import injea.knwremodel.Like.LikeService;
import injea.knwremodel.notice.Notice;
import injea.knwremodel.notice.NoticeController;
import injea.knwremodel.notice.NoticeService;
import injea.knwremodel.User.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        List<Notice> topNotices = noticeS.findTopView(5);

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
        List<Comment> userComments = commentS.getCommentsByUser(currentUserDTO.id);
        model.addAttribute("comments", userComments);

        return "myPage";
    }

    @GetMapping("/scholarshipwiki")
    public String scholarshipwiki(Model model) {

        return "scholarshipwiki";
    }
} 