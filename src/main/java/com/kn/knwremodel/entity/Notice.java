package com.kn.knwremodel.entity;


import jakarta.persistence.*;
import lombok.*;

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
    private String writer;

    @Column(nullable = false)
    private String regdate;

    @Column(nullable = false)
    private int view;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String post;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String img;

    @Builder
    public Notice(int boardId, String title, String type, String writer,
                  String regdate, int view, String post, String img) {
        this.boardId = boardId;
        this.title = title;
        this.type = type;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
        this.post = post;
        this.img = img;
    }
}
