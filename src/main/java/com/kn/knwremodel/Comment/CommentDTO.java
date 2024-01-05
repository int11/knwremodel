package com.kn.knwremodel.Comment;

import lombok.Getter;


public class CommentDTO {
    @Getter
    public static class save {
        private Long noticeId;
        private String comment;
    }

    @Getter
    public static class modify {
        private Long commentId;
        private String comment;
    }

    @Getter
    public static class delete {
        private Long commentId;
    }

    @Getter
    public static class Comment {
        private Long id;
        private String email;
        private String comment;
        private String createdDate;
        private String modifiedDate;

        public Comment(com.kn.knwremodel.Comment.Comment comment) {
            this.id = comment.getId();

            String userEmail = comment.getUser().getEmail();
            int atIndex = userEmail.indexOf('@');

            if (atIndex != -1) {
                String username = userEmail.substring(0, atIndex);
                String maskedUsername = username.substring(0, 2) + "*".repeat(username.length() - 2);
                this.email = maskedUsername;
            }

            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
        }
    }
}