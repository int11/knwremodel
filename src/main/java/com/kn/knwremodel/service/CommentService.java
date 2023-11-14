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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final HttpSession httpSession;

    @Transactional
    public Long saveComment(CommentDTO.save dto) {
        UserDTO.Session currentUserDTO = (UserDTO.Session) httpSession.getAttribute("user");

        // 사용자가 "USER" 역할을 가지고 있는지 확인
        if (currentUserDTO == null || !currentUserDTO.getRole().equals("USER")) {
            throw new IllegalArgumentException("댓글 쓰기 실패: 권한이 없습니다.");
        }

        Notice notice = noticeRepository.findById(dto.getNoticeId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + dto.getNoticeId()));

        User loginUser = userRepository.findById(currentUserDTO.getId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 로그인 정보가 존재하지 않습니다."));

        if (dto.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 쓰기 실패: 빈 내용의 댓글은 작성할 수 없습니다.");
        }

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
    public Long deleteComment(CommentDTO.delete dto) {
        UserDTO.Session currentuserDTO = (UserDTO.Session) httpSession.getAttribute("user");

        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 삭제 실패: 해당 댓글이 존재하지 않습니다."));

        // 댓글 작성자 또는 ADMIN 역할을 가진 사용자만 삭제 가능
        if (!comment.getUser().getId().equals(currentuserDTO.getId()) && !currentuserDTO.getRole().equals("ADMIN")) {
            throw new IllegalArgumentException("댓글 삭제 실패: 해당 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepo.deleteById(dto.getCommentId());
        return comment.getId();
    }

    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepo.findCommentsByUserId(userId);
    }

}