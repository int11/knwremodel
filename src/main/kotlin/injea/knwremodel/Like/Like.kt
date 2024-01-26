package injea.knwremodel.Like

import com.fasterxml.jackson.annotation.JsonIgnore
import injea.knwremodel.User.User
import injea.knwremodel.notice.Notice
import jakarta.persistence.*

@Entity(name = "likes") //이거 이름 안바꿔 주면 충돌남 SQL 예약어라서..
class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
        private set

    @ManyToOne
    @JoinColumn(name = "notice_id")
    var notice: Notice? = null
        private set

    constructor(id: Long?, user: User?, notice: Notice?) {
        this.id = id
        this.user = user
        this.notice = notice
    }

    constructor()

    class LikeBuilder internal constructor() {
        private var id: Long? = null
        private var user: User? = null
        private var notice: Notice? = null
        fun id(id: Long?): LikeBuilder {
            this.id = id
            return this
        }

        @JsonIgnore
        fun user(user: User?): LikeBuilder {
            this.user = user
            return this
        }

        fun notice(notice: Notice?): LikeBuilder {
            this.notice = notice
            return this
        }

        fun build(): Like {
            return Like(this.id, this.user, this.notice)
        }

        override fun toString(): String {
            return "Like.LikeBuilder(id=" + this.id + ", user=" + this.user + ", notice=" + this.notice + ")"
        }
    }

    companion object {
        fun builder(): LikeBuilder {
            return LikeBuilder()
        }
    }
}