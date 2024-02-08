package injea.knwremodel.comment


class CommentDTO {
    open class Common(comment: Comment) {
        val id: Long = comment.id!!
        val noticeId = comment.notice.id
        var email: String
        val text = comment.text
        val createdDate = comment.createdDate
        val modifiedDate = comment.modifiedDate

        init {
            val fullemail = comment.user.email
            val index = fullemail.indexOf('@')
            val temp = fullemail.substring(0, index)
            this.email = temp.substring(0, 2) + "*".repeat(temp.length - 2)
        }
    }
}