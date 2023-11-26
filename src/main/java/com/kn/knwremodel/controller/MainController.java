package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.CommentDTO;
import com.kn.knwremodel.dto.KeywordDTO;
import com.kn.knwremodel.dto.NoticeDTO;
import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.dto.NoticeDTO.responsebody;
import com.kn.knwremodel.entity.*;
import com.kn.knwremodel.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/main")
@RestController
public class MainController {
    private final NoticeService noticeS;
    private final CollegeService collegeS;
    private final HttpSession httpSession;
    private final NoticeController noticeC;
    private final KeywordController keywordC;
    private final HaksaController haksaC;
    private final UserController userC;
    private final LikeController likeC;

    private final CommentController commentC;
    @GetMapping(value={"/"})
    public ResponseEntity test(@RequestParam(defaultValue = "1") Long page,
                       @RequestParam(defaultValue = "20") Long perPage,
                       @RequestParam(required = false) String major,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false, defaultValue = "") String keyword,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<>();
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");
        ResponseEntity currentUser = userC.requestUser(httpSession);

        if(currentuserDTO != null) {
            result.put("currentUser", currentUser.getBody());
        }

        ResponseEntity notice = noticeC.requestPage(new NoticeDTO.requestPage(major, type, keyword, page, perPage));
        ResponseEntity keywords = keywordC.requestKeyword(new KeywordDTO.request(keyword, request));
        ResponseEntity recentlyKeywords = keywordC.recentKeywords(new KeywordDTO.requestRecentlyKeyword(
                keyword, request, response));


        result.put("majorList", collegeS.findAllMajor());
        result.put("page", notice.getBody());
        result.put("keywordsRanking", keywords.getBody());
        result.put("recentlyKeywords", recentlyKeywords.getBody());

        return ResponseEntity.ok().body(result);

    }

    @GetMapping("/read/{noticeid}")
    public ResponseEntity findNotice(@PathVariable Long noticeid) throws Exception {

        ResponseEntity notice = noticeC.requestbody(new NoticeDTO.requestbody(noticeid));
        NoticeDTO.responsebody noticeBody = (responsebody) notice.getBody();
        ResponseEntity comments = commentC.findComment(noticeBody);

        List<CommentDTO.Comment> test = noticeBody.getComments();

        Map<String, Object> result = new HashMap<>();
        result.put("notice", notice);

        if (test != null && !test.isEmpty()) {
            result.put("comments", comments.getBody());
        }
        return ResponseEntity.ok().body(result);
    }


    @GetMapping(value="/haksa")
    public ResponseEntity findHaksa(){
        ResponseEntity haksas = haksaC.requestHaksa();
        return ResponseEntity.ok().body(haksas);
    }

    @GetMapping("/top5View")
    public ResponseEntity getTop5View(@PageableDefault(size = 5, sort = "view", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Notice> topNotices = noticeS.findTop5ByView(pageable);

        topNotices.sort(Comparator.comparing(Notice::getView).reversed()
                .thenComparing(Notice::getCreateDate, Comparator.reverseOrder()));

        return ResponseEntity.ok().body(topNotices);
    }

    @GetMapping("/myPage")
    public ResponseEntity showLikedNoticesAndComments() throws Exception {
        UserDTO.Session currentUserDTO = (UserDTO.Session)httpSession.getAttribute("user");
        ResponseEntity currentUser = userC.requestUser(httpSession);
        Map<String, Object> result = new HashMap<>();

        if(currentUserDTO != null) {
            result.put("currentUser", currentUser.getBody());
        }

        ResponseEntity likedNotices = likeC.requestLike(currentUserDTO);
        result.put("likedNotices", likedNotices.getBody());

        ResponseEntity userComments = commentC.findCommentsByUser(currentUserDTO);
        result.put("userComments", userComments.getBody());

        return ResponseEntity.ok().body(result);
    }
}