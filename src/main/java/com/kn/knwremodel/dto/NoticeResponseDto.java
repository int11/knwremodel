package com.kn.knwremodel.dto;

import com.kn.knwremodel.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class NoticeResponseDto {

    private int id;
    private int boardId;
    private String title;
    private String type;
    private String writer;
    private String regdate;
    private int view;
    private String post;
    private String img;

    private int userId;

    private List<CommentResponseDto> comments;


    public NoticeResponseDto (Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.writer = notice.getWriter();
        this.post = notice.getPost();
        this.view = notice.getView();
        this.boardId = notice.getBoardId();
        this.type = notice.getType();
        this.regdate = notice.getRegdate();
        this.img = notice.getImg();
        this.comments = notice.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}