package com.kn.knwremodel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class NoticeDTO {
    private Long id;
    private String title;
    private String type;
    private String writer;
    private String regdate;
    private String view;
    private String post;
    private String img;

    @Builder
    public NoticeDTO(Long id, String title, String type,
                     String writer, String regdate, String view, String post, String img) {
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