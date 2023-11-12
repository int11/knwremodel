package com.kn.knwremodel.dto;

import com.kn.knwremodel.entity.User;
import lombok.*;


public class CommentDTO {
    @Getter
    public static class save{
        private Long noticeId;
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

    @Getter
    public static class Comment{
        private Long id;
        private User user;
        private String comment;
        private String createdDate;
        private String modifiedDate;

        public Comment(com.kn.knwremodel.entity.Comment comment) {
            this.id = comment.getId();
            this.user = comment.getUser();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
        }
    }
}