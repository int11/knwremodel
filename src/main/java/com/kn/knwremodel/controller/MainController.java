package com.kn.knwremodel.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kn.knwremodel.dto.KeywordDTO;
import com.kn.knwremodel.dto.NoticeDTO;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.service.CollegeService;
import com.kn.knwremodel.service.NoticeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/main")
@RestController
public class MainController {
    private final NoticeService noticeS;
    private final CollegeService collegeS;
    private final NoticeController noticeC;
    private final KeywordController keywordC;
    private final HaksaController haksaC;
    private final UserController userC;

    @GetMapping(value={"/"})
    public ResponseEntity test(@RequestParam(defaultValue = "1") Long page,
                       @RequestParam(defaultValue = "20") Long perPage,
                       @RequestParam(required = false) String major,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false, defaultValue = "") String keyword,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<>();
  
        ResponseEntity currentUser = userC.request();
        ResponseEntity notice = noticeC.requestPage(new NoticeDTO.requestPage(major, type, keyword, page, perPage));
        ResponseEntity keywords = keywordC.requestKeyword(new KeywordDTO.request(keyword, request));
        ResponseEntity recentlyKeywords = keywordC.recentKeywords(new KeywordDTO.requestRecentlyKeyword(
                keyword, request, response));

        result.put("currentUser", currentUser.getBody());
        result.put("majorList", collegeS.findAllMajor());
        result.put("page", notice.getBody());
        result.put("keywordsRanking", keywords.getBody());
        result.put("recentlyKeywords", recentlyKeywords.getBody());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/read/{noticeid}")
    public ResponseEntity findNotice(@PathVariable Long noticeid) throws Exception {
        return ResponseEntity.ok().body(noticeC.requestbody(new NoticeDTO.requestbody(noticeid)));
    }


    @GetMapping(value="/haksa")
    public ResponseEntity findHaksa(){
        ResponseEntity haksas = haksaC.request();
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
        Map<String, Object> result = new HashMap<>();
        ResponseEntity likedNotices = userC.likes();
        ResponseEntity userComments = userC.comments();

        result.put("likedNotices", likedNotices.getBody());
        result.put("userComments", userComments.getBody());
        return ResponseEntity.ok().body(result);
    }
}