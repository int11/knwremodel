package com.kn.knwremodel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.knwremodel.dto.LikeDTO;
import com.kn.knwremodel.service.LikeService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeS;

    @PostMapping("/click")
    public ResponseEntity clickLike(@RequestBody LikeDTO.click dto) throws Exception {
        try{
            return ResponseEntity.ok(likeS.clickLike(dto));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}