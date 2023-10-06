package com.kn.knwremodel.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(nullable = false)
    private int boardId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String regdate;

    @Column(nullable = false)
    private int view;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String post;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String img;

    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    @Builder
    public Notice(int boardId, String title, String type, String major, String writer,
                  String regdate, int view, String post, String img) {
        this.boardId = boardId;
        this.title = title;
        this.type = type;
        this.major = major;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
        this.post = post;
        this.img = img;
        this.comments = getComments();
    }
}
