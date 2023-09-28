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
    private Long id;

    @Column(nullable = false)
    private Long board_id;

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

    @Builder
    public Notice(Long board_id, String title, String type, String writer, String regdate, String view ) {
        this.board_id = board_id;
        this.title = title;
        this.type = type;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
    }
}