package com.kn.knwremodel.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String regdate;

    @Column(nullable = false)
    private String view;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String post;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String img;


    @Builder
    public Notice(Long id, String title, String type, String writer,
                  String regdate, String view, String post, String img) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
        this.post = post;
        this.img = img;
    }
}
