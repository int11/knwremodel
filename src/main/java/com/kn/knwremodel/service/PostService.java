package com.kn.knwremodel.service;

import com.kn.knwremodel.entity.Posts;
import com.kn.knwremodel.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(String title, String content, String writer) {
        return postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build()).getId();
    }

    @Transactional
    public Long update(Long id, String title, String content) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(title, content);
        return id;
    }

    public Posts findById(Long id) {
        return postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    }

    @Transactional(readOnly = true)
    public List<Posts> findAllDesc() {
        return postsRepository.findAllDesc();
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id=" + id));

        postsRepository.delete(posts);
    }
}
