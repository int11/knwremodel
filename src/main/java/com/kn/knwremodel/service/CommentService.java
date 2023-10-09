package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.CommentDto;
import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.repository.CommentRepository;
import com.kn.knwremodel.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final NoticeRepository noticeRepository;


    @Transactional
    public Long saveComment(CommentDto.save dto) {
        Notice notice = noticeRepository.findById(dto.getNoticeId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + dto.getNoticeId()));

        Comment comment = Comment.builder()
                .comment(dto.getComment())
                .user(dto.getUser())
                .notice(notice)
                .build();

        commentRepo.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long modifyComment(CommentDto.modify dto) {
        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 수정 실패: 해당 댓글이 존재하지 않습니다."));

        comment.setComment(dto.getComment());
        return comment.getId();
    }

    @Transactional
    public Long deleteComment(CommentDto.delete dto) {
        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 삭제 실패: 해당 댓글이 존재하지 않습니다."));
        commentRepo.deleteById(dto.getCommentId());
        return comment.getId();
    }

}