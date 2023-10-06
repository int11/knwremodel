package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.CommentDto;
import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.CommentRepository;
import com.kn.knwremodel.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public Long commentSave(Long id, CommentDto dto) {

        Notice notice = noticeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));

        dto.setNotice(notice);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getId();
    }
}