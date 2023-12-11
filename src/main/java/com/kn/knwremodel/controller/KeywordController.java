package com.kn.knwremodel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.knwremodel.dto.KeywordDTO;
import com.kn.knwremodel.entity.Keyword;
import com.kn.knwremodel.service.KeywordService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/keyword")
@RestController
public class KeywordController {
    private final KeywordService keywordS;
    @PostMapping("/add/rankingRecord")
    public ResponseEntity AddRankingKeyword(@RequestBody KeywordDTO.request dto, HttpServletRequest httpServletRequest) {
        keywordS.addRankingKeyword(dto, httpServletRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/add/recentRecord")
    public ResponseEntity AddRecentKeywords(@RequestBody KeywordDTO.requestRecentlyKeyword dto,
                                         HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        keywordS.addRecentKeywords(dto, httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/request/rankingRecord")
    public ResponseEntity requestRankingKeyword() {
        List<Keyword> keywords = keywordS.requestFindTop6ByKeyword();
        return ResponseEntity.ok(keywords);
    }
    @PostMapping("/request/recentRecord")
    public ResponseEntity requestKeyword(HttpServletRequest httpServletRequest) {
        List<String> keywords = keywordS.requestRecentKeywords(httpServletRequest);
        return ResponseEntity.ok(keywords);
    }

}
