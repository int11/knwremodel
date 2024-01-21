package injea.knwremodel.Notice;


import com.fasterxml.jackson.annotation.JsonIgnore;
import injea.knwremodel.Comment.Comment;
import injea.knwremodel.Like.Like;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notices")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = true)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private LocalDate regdate;

    @Column(nullable = false)
    private Long view;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String body;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String img;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String html;
    
    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    @Column(nullable = false)
    private Long likeCount;

    @Builder
    public Notice(Long boardId, String title, String type, String major, String writer,
                  LocalDate regdate, Long view, String body, String img, String html) {
        this.boardId = boardId;
        this.title = title;
        this.type = type;
        this.major = major;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
        this.body = body;
        this.img = img;
        this.html = html;
        this.comments = getComments();
        this.likeCount = 0L;
    }

    public void updateLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDate getCreateDate() {
        return regdate;
    }


}
