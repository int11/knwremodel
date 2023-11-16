package com.kn.knwremodel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long view;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String body;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String img;

    public Event(String title, String type, String writer, String regdate, Long view,
                 String body, String img) {
        this.title = title;
        this.type = type;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
        this.body = body;
        this.img = img;
    }
}
