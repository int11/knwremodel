package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.CommentDto;
import com.kn.knwremodel.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class CommentController {

    private final CommentService commentS;

    /* CREATE */
    @PostMapping("/comments/save/")
    public ResponseEntity saveComment(@RequestBody CommentDto.save commentdto) {
        return ResponseEntity.ok(commentS.saveComment(commentdto));
    }

    @PostMapping("/comments/modify/")
    public ResponseEntity modifyComment(@RequestBody CommentDto.modify commentdto) {
        return ResponseEntity.ok(commentS.modifyComment(commentdto));
    }
}