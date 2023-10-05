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

    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentDto dto) {
        return ResponseEntity.ok(commentService.commentSave(id, dto));
    }
}