package injea.knwremodel.notice

import injea.knwremodel.comment.Comment
import injea.knwremodel.comment.CommentDTO
import injea.knwremodel.like.LikeService
import java.time.LocalDateTime

class NoticeDTO {
    class CommonWithoutBody(likeS: LikeService, notice: Notice) {
        var id: Long = notice.id!!
        var boardId: Long? = notice.boardId
        var title: String = notice.title
        var major: String = notice.major
        var type: String = notice.type
        var writer: String = notice.writer
        var regdate: LocalDateTime = notice.regdate
        var view: Long = notice.view
        var likeCount: Long = notice.likeCount
        var isCheckLike: Boolean = likeS.checkedLike(this.id)
    }

    class Common(likeS: LikeService, notice: Notice) {
        val id: Long = notice.id!!
        val boardId: Long? = notice.boardId
        val title: String = notice.title
        val major: String = notice.major
        val type: String = notice.type
        val writer: String = notice.writer
        val regdate: LocalDateTime = notice.regdate
        val view: Long = notice.view
        val likeCount: Long = notice.likeCount
        val isCheckLike: Boolean = likeS.checkedLike(this.id)

        val body: String = notice.body
        val img: String = notice.img
        val html: String = notice.html

        val comments: List<CommentDTO.Common> = notice.comments.map { comment: Comment -> CommentDTO.Common(comment) }
    }
}