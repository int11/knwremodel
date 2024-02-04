package injea.knwremodel.notice

import com.fasterxml.jackson.annotation.JsonIgnore
import injea.knwremodel.comment.Comment
import injea.knwremodel.like.Like
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name= "notices")
class Notice(
    boardId: Long?,
    title: String,
    major: String,
    type: String,
    writer: String,
    regdate: LocalDateTime,
    view: Long,
    body: String,
    img: String,
    html: String,

    likeCount: Long = 0
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long? = null

    var boardId = boardId
        protected set
    var title = title
        protected set
    var major = major
        protected set
    var type = type
        protected set
    var writer = writer
        protected set
    var regdate = regdate
        protected set
    var view = view
        protected set

    @Column(columnDefinition = "TEXT")
    var body = body
        protected set

    @Column(columnDefinition = "TEXT")
    var img = img
        protected set

    @Column(columnDefinition = "TEXT")
    var html = html
        protected set

    @JsonIgnore
    @OneToMany(mappedBy = "notice", cascade = [CascadeType.REMOVE])
    val comments: MutableList<Comment> = mutableListOf()

    @JsonIgnore
    @OneToMany(mappedBy = "notice", cascade = [CascadeType.REMOVE])
    val likes: MutableList<Like> = mutableListOf()

    @Column(nullable = false)
    var likeCount = likeCount
}
