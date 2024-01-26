package injea.knwremodel.Comment


class CommentDTO {
    class save {
        val noticeId: Long? = null
        val comment: String? = null
    }

    class modify {
        val commentId: Long? = null
        val comment: String? = null
    }

    class delete {
        val commentId: Long? = null
    }

    class Comment(comment: injea.knwremodel.Comment.Comment) {
        val id: Long? = comment.id
        var email: String? = null
        val comment: String?
        val createdDate: String
        val modifiedDate: String

        init {
            val userEmail = comment.user.email
            val atIndex = userEmail!!.indexOf('@')

            if (atIndex != -1) {
                val username = userEmail.substring(0, atIndex)
                val maskedUsername = username.substring(0, 2) + "*".repeat(username.length - 2)
                this.email = maskedUsername
            }

            this.comment = comment.comment
            this.createdDate = comment.createdDate
            this.modifiedDate = comment.modifiedDate
        }
    }
}