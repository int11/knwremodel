package injea.knwremodel.Notice

import com.fasterxml.jackson.annotation.JsonIgnore
import injea.knwremodel.Comment.Comment
import injea.knwremodel.Like.Like
import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "notices")
class Notice(
    boardId: Long?,
    title: String,
    major: String,
    type: String,
    writer: String,
    createDate: LocalDate,
    view: Long,
    body: String,
    img: String?,
    html: String,

    likeCount: Long = 0
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private val id: Long? = null

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
    var createDate = createDate
        protected set
    var view = view
        protected set

    @Column(columnDefinition = "TEXT")
    var body = body
        protected set

    @Column(columnDefinition = "TEXT")
    var img = img
        protected set

    @Column(columnDefinition = "TEXT", nullable = false)
    var html = html
        protected set

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    @OrderBy("id asc") // 댓글 정렬
    val comments: MutableList<Comment> = mutableListOf()

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    val likes: MutableList<Like> = mutableListOf()

    @Column(nullable = false)
    var likeCount = likeCount
        protected  set
}
