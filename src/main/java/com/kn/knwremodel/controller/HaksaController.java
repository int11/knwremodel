package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.KeywordDTO;
import com.kn.knwremodel.entity.Haksa;
import com.kn.knwremodel.entity.Keyword;
import com.kn.knwremodel.service.HaksaService;
import com.kn.knwremodel.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/haksa")
@RestController
public class HaksaController {

    private final HaksaService haksaS;
    @PostMapping("/request")
    public ResponseEntity requestHaksa() {
        List<Haksa> keywords = haksaS.findAll();
        return ResponseEntity.ok(keywords);
    }
}
