package com.kn.knwremodel.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeDTO{
    private Long id;
    private Long board_id;
    private String title;
    private String type;
    private String writer;
    private String regdate;
    private String view;
    @Builder
    public NoticeDTO(Long board_id, String title, String type, String writer, String regdate, String view ) {
        this.board_id = board_id;
        this.title = title;
        this.type = type;
        this.writer = writer;
        this.regdate = regdate;
        this.view = view;
    }
}