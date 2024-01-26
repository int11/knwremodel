package injea.knwremodel.comment

import injea.knwremodel.comment.CommentDTO.delete
import injea.knwremodel.comment.CommentDTO.modify
import injea.knwremodel.User.UserDTO
import injea.knwremodel.User.UserRepository
import injea.knwremodel.notice.NoticeRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepo: CommentRepository,
    private val userRepo: UserRepository,
    private val noticeRepo: NoticeRepository,
    private val httpSession: HttpSession
) {
    @Transactional
    fun saveComment(dto: CommentDTO.save): Long? {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session

        // 사용자가 "USER" 역할을 가지고 있는지 확인
        requireNotNull(currentuserDTO) { "댓글 쓰기 실패: 로그인 정보가 존재하지 않습니다." }
        require(currentuserDTO.role == "ROLE_USER") { "댓글 쓰기 실패: 권한이 없습니다." }

        val notice = noticeRepo.findById(dto.noticeId)
            .orElseThrow { IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + dto.noticeId) }!!

        val loginUser = userRepo.findById(currentuserDTO.id).get()

        val comment: Comment = Comment.Companion.builder()
            .comment(dto.comment)
            .user(loginUser)
            .notice(notice)
            .build()

        commentRepo.save(comment)
        return comment.id
    }

    @Transactional
    @Throws(Exception::class)
    fun modifyComment(dto: modify): Long? {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session

        val comment = commentRepo.findById(dto.commentId)
            .orElseThrow { IllegalArgumentException("댓글 수정 실패: 해당 댓글이 존재하지 않습니다.") }!!

        requireNotNull(currentuserDTO) { "댓글 수정 실패: 로그인 정보가 존재하지 않습니다." }
        require(!dto.comment.trim { it <= ' ' }.isEmpty()) { "댓글 수정 실패: 빈 내용의 댓글은 작성할 수 없습니다." }

        comment.comment = dto.comment
        return comment.id
    }

    @Transactional
    fun deleteComment(dto: delete): Long? {
        val currentuserDTO = httpSession.getAttribute("user") as UserDTO.Session

        val comment = commentRepo.findById(dto.commentId)
            .orElseThrow { IllegalArgumentException("댓글 삭제 실패: 해당 댓글이 존재하지 않습니다.") }!!

        requireNotNull(currentuserDTO) { "댓글 삭제 실패: 로그인 정보가 존재하지 않습니다." }
        require(!(comment.user.id != currentuserDTO.id && currentuserDTO.role != "ROLE_ADMIN")) { "댓글 삭제 실패: 해당 댓글을 작성한 사용자가 아닙니다." }

        commentRepo.deleteById(dto.commentId)
        return comment.id
    }

    fun getCommentsByUser(userId: Long?): List<Comment?>? {
        return commentRepo.findCommentsByUserId(userId)
    }
}