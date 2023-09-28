package com.kn.knwremodel.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class noticeDTO{
    private Long id;
    private String board_id;
    private String title;
    private String type;
    private String writer;
    private String regdate;
    private String view;
}