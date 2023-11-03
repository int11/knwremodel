package com.kn.knwremodel.service;

import com.kn.knwremodel.dto.CommentDTO;
import com.kn.knwremodel.dto.UserDTO;
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

    @Transactional
    public Long saveComment(CommentDTO.save dto) {
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");

        Notice notice = noticeRepository.findById(dto.getNoticeId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + dto.getNoticeId()));

        User loginUser = userRepository.findById(currentuserDTO.getId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 로그인 정보가 존재하지 않습니다."));

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
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");

        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 수정 실패: 해당 댓글이 존재하지 않습니다."));

        //추가
        if (comment.getUser().getId() != currentuserDTO.getId())
            throw new Exception("댓글 수정 실패: 해당 댓글을 작성한 사용자가 아님");

        comment.setComment(dto.getComment());
        return comment.getId();
    }

    @Transactional
    public Long deleteComment(CommentDTO.delete dto) throws Exception{
        UserDTO.Session currentuserDTO = (UserDTO.Session)httpSession.getAttribute("user");

        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 삭제 실패: 해당 댓글이 존재하지 않습니다."));
        //추가

        if (comment.getUser().getId() != currentuserDTO.getId())
            throw new Exception("댓글 삭제 실패: 해당 댓글을 작성한 사용자가 아님");

        commentRepo.deleteById(dto.getCommentId());
        return comment.getId();
    }

}