package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.CommentDTO;
import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.User;
import com.kn.knwremodel.repository.CommentRepository;
import com.kn.knwremodel.repository.NoticeRepository;
import com.kn.knwremodel.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final HttpSession httpSession;

    @Setter
    private Long loginUserId;

    @Transactional
    public Long saveComment(CommentDTO.save dto) {
        System.out.println("test값은: " + loginUserId);

        Notice notice = noticeRepository.findById(dto.getNoticeId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + dto.getNoticeId()));

        User loginUser = userRepository.findById(loginUserId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 로그인 정보가 존재하지 않습니다." + loginUserId));

        Comment comment = Comment.builder()
                .comment(dto.getComment())
                .user(loginUser)
                .notice(notice)
                .build();

        commentRepo.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long modifyComment(CommentDTO.modify dto) throws Exception{
        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 수정 실패: 해당 댓글이 존재하지 않습니다."));

        //추가
        if (comment.getUser().getId() != loginUserId)
            throw new Exception("댓글 수정 실패: 해당 댓글을 작성한 사용자가 아님");

        comment.setComment(dto.getComment());
        return comment.getId();
    }

    @Transactional
    public Long deleteComment(CommentDTO.delete dto) throws Exception{
        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 삭제 실패: 해당 댓글이 존재하지 않습니다."));
        //추가

        if (comment.getUser().getId() != loginUserId)
            throw new Exception("댓글 수정 실패: 해당 댓글을 작성한 사용자가 아님");

        commentRepo.deleteById(dto.getCommentId());
        return comment.getId();
    }

}