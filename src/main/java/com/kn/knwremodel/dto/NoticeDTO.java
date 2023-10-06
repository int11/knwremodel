package com.kn.knwremodel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@ToString
public class NoticeDTO {
    private int id;
    private int boardId;
    private String title;
    private String type;
    private String major;
    private String writer;
    private String regdate;
    private int view;
    private String post;
    private String img;
    

    @Builder
    public NoticeDTO(int boardId, String title, String type, String major,
                     String writer, String regdate, int view, String post, String img) {
        this.boardId = boardId;
        this.title = title;
        this.type = type;
        this.major = major;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
        this.post = post;
        this.img = img;
    }
}