package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.KeywordDTO;
import com.kn.knwremodel.entity.Keyword;
import com.kn.knwremodel.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/keyword")
@RestController
public class KeywordController {
    private final KeywordService keywordS;
    @PostMapping("/ranking")
    public ResponseEntity requestKeyword(@RequestBody KeywordDTO.request dto) {
        List<Keyword> keywords = keywordS.findTop5ByKeyword(dto);
        return ResponseEntity.ok(keywords);
    }

    @PostMapping("/recentRecord")
    public ResponseEntity recentKeywords(@RequestBody KeywordDTO.requestRecentlyKeyword dto) {
        List<String> keywords = keywordS.recentKeywords(dto);
        return ResponseEntity.ok(keywords);
    }
}
