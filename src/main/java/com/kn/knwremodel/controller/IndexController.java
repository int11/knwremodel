package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Posts;
import com.kn.knwremodel.repository.PostsRepository;
import com.kn.knwremodel.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    public String postsSave(Model model) {
        UserDTO.Session currentuserDTO = (UserDTO.Session) httpSession.getAttribute("user");
        if (currentuserDTO != null) {
            model.addAttribute("currentuser", currentuserDTO);
        }
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        Posts post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts-update";
    }

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody Posts requestDto) {
        return postService.save(requestDto.getTitle(), requestDto.getContent(), requestDto.getWriter());
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
