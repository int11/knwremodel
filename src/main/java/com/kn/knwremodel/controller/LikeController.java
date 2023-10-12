package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.LikeDTO;
import com.kn.knwremodel.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class LikeController {
    private final LikeService likeS;

    @PostMapping("/add/like/")
    public ResponseEntity addLike(@RequestBody LikeDTO.Request dto) throws Exception {
        return ResponseEntity.ok(likeS.addLike(dto));
    }

    @PostMapping("/delete/like/")
    public ResponseEntity deleteLike(@RequestBody LikeDTO.Request dto) throws Exception {
        return ResponseEntity.ok(likeS.deleteLike(dto));
    }
}