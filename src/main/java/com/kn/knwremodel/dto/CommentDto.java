package com.kn.knwremodel.dto;

import com.kn.knwremodel.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CommentDto {


    @Getter
    public static class save{
        private Long noticeId;
        private String user;
        private String comment;
    }

    @Getter
    public static class modify{
        private Long commentId;
        private String comment;
    }

    @Getter
    public static class delete{
        private Long commentId;
    }
}