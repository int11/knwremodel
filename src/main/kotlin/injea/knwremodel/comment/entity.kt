package injea.knwremodel.comment

import injea.knwremodel.user.User
import injea.knwremodel.entity.TimeEntity
import injea.knwremodel.notice.Notice
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener::class)
class Comment : TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null // 작성자
        private set

    @Column(columnDefinition = "TEXT", nullable = false)
    var comment: String? = null // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "notice_id")
    var notice: Notice? = null
        private set

    constructor(id: Long?, user: User?, comment: String?, notice: Notice?) {
        this.id = id
        this.user = user
        this.comment = comment
        this.notice = notice
    }

    protected constructor()

    class CommentBuilder internal constructor() {
        private var id: Long? = null
        private var user: User? = null
        private var comment: String? = null
        private var notice: Notice? = null
        fun id(id: Long?): CommentBuilder {
            this.id = id
            return this
        }

        fun user(user: User?): CommentBuilder {
            this.user = user
            return this
        }

        fun comment(comment: String?): CommentBuilder {
            this.comment = comment
            return this
        }

        fun notice(notice: Notice?): CommentBuilder {
            this.notice = notice
            return this
        }

        fun build(): Comment {
            return Comment(this.id, this.user, this.comment, this.notice)
        }

        override fun toString(): String {
            return "Comment.CommentBuilder(id=" + this.id + ", user=" + this.user + ", comment=" + this.comment + ", notice=" + this.notice + ")"
        }
    }

    companion object {
        fun builder(): CommentBuilder {
            return CommentBuilder()
        }
    }
}