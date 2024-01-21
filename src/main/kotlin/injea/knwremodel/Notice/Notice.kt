package injea.knwremodel.Notice

import com.fasterxml.jackson.annotation.JsonIgnore
import injea.knwremodel.Comment.Comment
import injea.knwremodel.Like.Like
import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "notices")
class Notice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long,

    val boardId: Long? = null,
    val title: String,
    val major: String,
    val type: String,
    val writer: String,
    val createDate: LocalDate,
    val view: Long,

    @Column(columnDefinition = "TEXT")
    val body: String?,

    @Column(columnDefinition = "TEXT")
    val img: String?,

    @Column(columnDefinition = "TEXT", nullable = false)
    val html: String?,

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    @OrderBy("id asc") // 댓글 정렬
    val comments: List<Comment> = mutableListOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    val likes: List<Like> = mutableListOf(),

    @Column(nullable = false)
    var likeCount: Long=0
)
