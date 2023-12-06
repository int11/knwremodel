package com.kn.knwremodel.controller;

import com.kn.knwremodel.dto.UserDTO;
import com.kn.knwremodel.entity.Posts;
import com.kn.knwremodel.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/dev")
@Controller
public class IndexController {
    private final HttpSession httpSession;
    private final PostService postService;

    @GetMapping("/notice")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAllDesc());
        return "index.html";
    }

    @GetMapping("/savenotice")
    public String postsSave(Model model) {
        UserDTO.Session currentuserDTO = (UserDTO.Session) httpSession.getAttribute("user");
        if (currentuserDTO != null) {
            model.addAttribute("currentuser", currentuserDTO);
        }
        return "posts-save";
    }

    @GetMapping("/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        Posts post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts-update";
    }

    @PostMapping("/save")
    public Long save(@RequestBody Posts requestDto) {
        return postService.save(requestDto.getTitle(), requestDto.getContent(), requestDto.getWriter());
    }

    @PutMapping("/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody Posts requestDto) {
        return postService.update(id, requestDto.getTitle(), requestDto.getContent());
    }

    @GetMapping("/api/v1/posts/{id}")
    public Posts findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }
}
