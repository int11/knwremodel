package com.kn.knwremodel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.service.CommentService;
import com.kn.knwremodel.service.LikeService;
import com.kn.knwremodel.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userS;
    private final HttpSession httpSession;
    private final CommentService commentS;
    private final LikeService likeS;

    @PostMapping("/saveDepartment")
    public ResponseEntity saveDepartment(@RequestParam String department) {
        try {
            userS.setDepartment(department);
            return ResponseEntity.ok("학부 저장을 성공했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("학부 저장을 실패했습니다.");
        }
    }

    @PostMapping("/request")
    public ResponseEntity request() {
        try {
            UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");
            if(currentuserDTO != null) {
                return ResponseEntity.ok(currentuserDTO);
            }
            return ResponseEntity.ok(false);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }  

    @PostMapping("/likes")
    public ResponseEntity likes() throws Exception {
        try {
            UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");
            if(currentuserDTO != null) {
                return ResponseEntity.ok(likeS.getLikedNotices(currentuserDTO));
            }
            return ResponseEntity.ok(false);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/comments")
    public ResponseEntity comments() {
        try {
            UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");
            if(currentuserDTO != null) {
                return ResponseEntity.ok(commentS.getCommentsByUser(currentuserDTO.getId()));
            }
            return ResponseEntity.ok(false);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
