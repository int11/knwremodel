package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Posts;
import com.kn.knwremodel.repository.PostsRepository;
import com.kn.knwremodel.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class IndexController {
    private final HttpSession httpSession;
    private final PostService postService;
    private final PostsRepository postsRepository;
    @GetMapping("/notice")
    public List<Posts> findAllDesc(Model model) {
        model.addAttribute("posts", postService.findAllDesc());
        return postsRepository.findAllDesc();
    }

    @GetMapping("/posts/save")
    public ResponseEntity<UserDTO.Session> getCurrentUser(HttpSession httpSession) {
        UserDTO.Session currentUserDTO = (UserDTO.Session) httpSession.getAttribute("user");

        if (currentUserDTO != null) {
            return ResponseEntity.ok(currentUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        Posts post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts-update";
    }

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody Posts requestDto) {
        // 세션에서 이메일 가져오기
        UserDTO.Session currentUserDTO = (UserDTO.Session) httpSession.getAttribute("user");
        if (currentUserDTO != null) {
            // 세션에서 가져온 이메일을 작성자로 설정
            requestDto.setWriter(currentUserDTO.getEmail());
            return postService.save(requestDto.getTitle(), requestDto.getContent(), requestDto.getWriter());
        } else {
            // 세션이 없을 경우 예외 처리 또는 다른 방법을 선택하세요.
            throw new IllegalStateException("세션에 사용자 정보가 없습니다.");
        }
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody Posts requestDto) {
        return postService.update(id, requestDto.getTitle(), requestDto.getContent());
    }

    @GetMapping("/api/v1/posts/{id}")
    public Posts findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }
}
