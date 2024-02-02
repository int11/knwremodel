package injea.knwremodel.comment

import injea.knwremodel.user.User
import injea.knwremodel.entity.TimeEntity
import injea.knwremodel.notice.Notice
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener::class)
class Comment(user: User, notice: Notice, text: String) : TimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long? = null

    @Column(columnDefinition = "TEXT", nullable = false)
    var text = text

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user = user // 작성자
        protected set

    @ManyToOne
    @JoinColumn(name = "notice_id")
    var notice = notice
        protected set
}