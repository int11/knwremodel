package injea.knwremodel.comment


class CommentDTO {
    class Common(comment: Comment) {
        val id: Long = comment.id!!
        var email: String
        val text = comment.text
        val createdDate = comment.createdDate
        val modifiedDate = comment.modifiedDate

        init {
            val userEmail = comment.user.email
            val atIndex = userEmail.indexOf('@')
            val username = userEmail.substring(0, atIndex)
            val maskedUsername = username.substring(0, 2) + "*".repeat(username.length - 2)
            this.email = maskedUsername
        }
    }
}