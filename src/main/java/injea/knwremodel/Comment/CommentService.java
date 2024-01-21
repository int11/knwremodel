package injea.knwremodel.Comment;

import injea.knwremodel.Notice.Notice;
import injea.knwremodel.Notice.NoticeRepository;
import injea.knwremodel.User.User;
import injea.knwremodel.User.UserDTO;
import injea.knwremodel.User.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;
    private final NoticeRepository noticeRepo;
    private final HttpSession httpSession;

    @Transactional
    public Long saveComment(CommentDTO.save dto) {
        UserDTO.Session currentuserDTO = (UserDTO.Session) httpSession.getAttribute("user");

        // 사용자가 "USER" 역할을 가지고 있는지 확인
        if (currentuserDTO == null) {
            throw new IllegalArgumentException("댓글 쓰기 실패: 로그인 정보가 존재하지 않습니다.");
        } else if (!currentuserDTO.getRole().equals("ROLE_USER")){
            throw new IllegalArgumentException("댓글 쓰기 실패: 권한이 없습니다.");
        } else if (dto.getComment().trim().isEmpty()){
            throw new IllegalArgumentException("댓글 쓰기 실패: 빈 내용의 댓글은 작성할 수 없습니다.");
        }

        Notice notice = noticeRepo.findById(dto.getNoticeId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + dto.getNoticeId()));

        User loginUser = userRepo.findById(currentuserDTO.getId()).get();

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

        if (currentuserDTO == null) {
            throw new IllegalArgumentException("댓글 수정 실패: 로그인 정보가 존재하지 않습니다.");
        } else if (dto.getComment().trim().isEmpty()){
            throw new IllegalArgumentException("댓글 수정 실패: 빈 내용의 댓글은 작성할 수 없습니다.");
        } else if (!comment.getUser().getId().equals(currentuserDTO.getId())){
            throw new IllegalArgumentException("댓글 수정 실패: 해당 댓글을 작성한 사용자가 아닙니다.");
        }

        comment.setComment(dto.getComment());
        return comment.getId();
    }

    @Transactional
    public Long deleteComment(CommentDTO.delete dto) {
        UserDTO.Session currentuserDTO = (UserDTO.Session) httpSession.getAttribute("user");

        Comment comment = commentRepo.findById(dto.getCommentId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 삭제 실패: 해당 댓글이 존재하지 않습니다."));

        if (currentuserDTO == null) {
            throw new IllegalArgumentException("댓글 삭제 실패: 로그인 정보가 존재하지 않습니다.");
        } else if (!comment.getUser().getId().equals(currentuserDTO.getId()) && !currentuserDTO.getRole().equals("ROLE_ADMIN")){
            throw new IllegalArgumentException("댓글 삭제 실패: 해당 댓글을 작성한 사용자가 아닙니다.");
        }

        commentRepo.deleteById(dto.getCommentId());
        return comment.getId();
    }

    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepo.findCommentsByUserId(userId);
    }

}