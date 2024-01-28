package injea.knwremodel.like

import com.fasterxml.jackson.annotation.JsonIgnore
import injea.knwremodel.user.User
import injea.knwremodel.notice.Notice
import jakarta.persistence.*

@Entity
@Table(name = "likes")
class Like(
    user: User,
    notice: Notice
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user = user
        protected set

    @ManyToOne
    @JoinColumn(name = "notice_id")
    var notice = notice
        protected set
}