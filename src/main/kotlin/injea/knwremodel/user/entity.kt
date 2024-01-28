package injea.knwremodel.user

import com.fasterxml.jackson.annotation.JsonIgnore
import injea.knwremodel.comment.Comment
import injea.knwremodel.like.Like
import injea.knwremodel.entity.Role
import injea.knwremodel.entity.TimeEntity
import jakarta.persistence.*


@Entity
class User(
    name: String,
    email: String,
    picture: String,
    role: Role
) : TimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long? = null

    var name = name
        protected set

    var email = email
        protected set

    var picture = picture
        protected set

    @Enumerated(EnumType.STRING)
    var role = role

    var department: String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    val comments: MutableList<Comment> = mutableListOf()

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    val likes: MutableList<Like> = mutableListOf()

    fun update(name: String, picture: String): User {
        this.name = name
        this.picture = picture
        return this
    }
}