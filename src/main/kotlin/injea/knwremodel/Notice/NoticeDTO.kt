package injea.knwremodel.Notice

import injea.knwremodel.Comment.Comment
import injea.knwremodel.Comment.CommentDTO
import injea.knwremodel.Like.LikeService
import java.time.LocalDateTime
import java.util.stream.Collectors

class NoticeDTO {
    class requestPage(
        var major: String?,
        var type: String?,
        var keyword: String?,
        var page: Long,
        var perPage: Long)


    class responsePage(likeS: LikeService, notice: Notice) {
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
        var img: String? = notice.img
    }

    class requestbody(var id: Long)

    class responsebody(likeS: LikeService, notice: Notice) {
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
        val img: String? = notice.img
        val html: String = notice.html
        @JvmField
        val comments: List<CommentDTO.Comment> =
            notice.comments.stream().map { comment: Comment? -> CommentDTO.Comment(comment) }
                .collect(Collectors.toList())
    }

    class toplike(var major: String?, var size: Int)

    class topview(var size: Int)
}