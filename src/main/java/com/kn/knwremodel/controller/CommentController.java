package com.kn.knwremodel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.knwremodel.dto.CommentDTO;
import com.kn.knwremodel.service.CommentService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentS;

    /* CREATE */
    @PostMapping("/save")
    public ResponseEntity saveComment(@RequestBody CommentDTO.save commentdto) throws Exception {
        try{
            return ResponseEntity.ok(commentS.saveComment(commentdto));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/modify")
    public ResponseEntity modifyComment(@RequestBody CommentDTO.modify commentdto) throws Exception {
        try{
            return ResponseEntity.ok(commentS.modifyComment(commentdto));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteComment(@RequestBody CommentDTO.delete commentdto) throws Exception {
        try{
            return ResponseEntity.ok(commentS.deleteComment(commentdto));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}