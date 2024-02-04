package injea.knwremodel.comment

import injea.knwremodel.notice.NoticeService
import injea.knwremodel.user.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepo: CommentRepository,
    private val userS: UserService,
    private val noticeS: NoticeService
) {
    @Transactional
    fun saveComment(noticeId: Long, text: String){
        val currentuser = userS.getCurrentUser()
        if (currentuser.role.key != "ROLE_USER")
            throw IllegalArgumentException("댓글 쓰기 실패: 권한이 없습니다.")

        val notice = noticeS.findById(noticeId)

        val comment = Comment(currentuser, notice, text)

        commentRepo.save(comment)
    }

    @Transactional
    @Throws(Exception::class)
    fun modifyComment(commentId: Long, text: String){
        val currentuser = userS.getCurrentUser()
        val comment = findById(commentId)
        require(text.isNotEmpty()) { "댓글 수정 실패: 빈 내용의 댓글은 작성할 수 없습니다." }
        if (comment.user.id == currentuser.id)
            throw IllegalArgumentException("댓글 수정 실패: 해당 댓글을 작성한 사용자가 아닙니다.")

    }

    @Transactional
    fun deleteComment(commentId: Long): Long? {
        val currentuser = userS.getCurrentUser()
        val comment = findById(commentId)
        if (comment.user.id != currentuser.id || currentuser.role.key != "ROLE_ADMIN")
            throw IllegalArgumentException("댓글 삭제 실패: 권한이 없습니다.")
        commentRepo.deleteById(commentId)
        return comment.id
    }

    fun findById(id: Long): Comment {
        //findById(id) 는 Optimal<type> 타입 반환 .orElse(null) 함수를 통해 kotlin "type?" 타입으로 변경
        return commentRepo.findById(id).orElse(null) ?: throw NullPointerException("댓글을 찾을 수 없습니다.")
    }
}