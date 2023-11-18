package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.CommentDTO;
import com.kn.knwremodel.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentS;

    /* CREATE */
    @PostMapping("/save")
    public ResponseEntity saveComment(@RequestBody CommentDTO.save commentdto) throws Exception {
        try{
            return ResponseEntity.ok(commentS.saveComment(commentdto));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/modify")
    public ResponseEntity modifyComment(@RequestBody CommentDTO.modify commentdto) throws Exception {
        try{
            return ResponseEntity.ok(commentS.modifyComment(commentdto));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteComment(@RequestBody CommentDTO.delete commentdto) throws Exception {
        try{
            return ResponseEntity.ok(commentS.deleteComment(commentdto));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}