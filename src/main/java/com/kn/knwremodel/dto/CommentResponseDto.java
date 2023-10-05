package com.kn.knwremodel.dto;

import com.kn.knwremodel.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String user;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private int postsId;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.user = comment.getUser();
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.postsId = comment.getNotice().getId();
    }
}
