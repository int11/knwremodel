package com.kn.knwremodel.dto;

import com.kn.knwremodel.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CommentDto {
    
    @Getter
    public class save{
        private Long noticeid;
        private String user;
        private String comment;
    }

    @Getter
    public class modify{
        private Long commentid;
        private String comment;
    }
}