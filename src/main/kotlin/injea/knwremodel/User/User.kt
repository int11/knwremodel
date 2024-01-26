package injea.knwremodel.User

import com.fasterxml.jackson.annotation.JsonIgnore
import injea.knwremodel.Comment.Comment
import injea.knwremodel.Like.Like
import injea.knwremodel.entity.Role
import injea.knwremodel.entity.TimeEntity
import jakarta.persistence.*


@Entity
class User : TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @Column(nullable = false)
    var name: String? = null
        private set

    @Column(nullable = false)
    var email: String? = null
        private set

    @Column
    var picture: String? = null
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role? = null

    @Column
    var department: String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    val comments: List<Comment> = ArrayList()

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    val likes: List<Like> = ArrayList()

    @Column
    var authKey: String? = null // 추가: 인증 키 필드
        private set

    constructor(id: Long?, name: String?, email: String?, picture: String?, role: Role?) {
        this.id = id
        this.name = name
        this.email = email
        this.picture = picture
        this.role = role
    }

    constructor()

    fun update(name: String?, picture: String?): User {
        this.name = name
        this.picture = picture
        return this
    }

    val roleKey: String
        get() = role!!.key

    class UserBuilder internal constructor() {
        private var id: Long? = null
        private var name: String? = null
        private var email: String? = null
        private var picture: String? = null
        private var role: Role? = null

        fun id(id: Long?): UserBuilder {
            this.id = id
            return this
        }

        fun name(name: String?): UserBuilder {
            this.name = name
            return this
        }

        fun email(email: String?): UserBuilder {
            this.email = email
            return this
        }

        fun picture(picture: String?): UserBuilder {
            this.picture = picture
            return this
        }

        fun role(role: Role?): UserBuilder {
            this.role = role
            return this
        }

        fun build(): User {
            return User(this.id, this.name, this.email, this.picture, this.role)
        }

        override fun toString(): String {
            return "User.UserBuilder(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ", picture=" + this.picture + ", role=" + this.role + ")"
        }
    }

    companion object {
        fun builder(): UserBuilder {
            return UserBuilder()
        }
    }
}