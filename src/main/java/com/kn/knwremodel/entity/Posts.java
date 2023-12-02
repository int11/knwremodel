package com.kn.knwremodel.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class Posts extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Builder
    public Posts(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}