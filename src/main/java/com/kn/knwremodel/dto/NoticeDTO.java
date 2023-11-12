package com.kn.knwremodel.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.service.LikeService;

import lombok.Getter;


public class NoticeDTO {
    @Getter
    public static class requestPage{
        private String major;
        private String type; 
        private String keyword;
        private Long page;
        private Long perPage;
    }

    @Getter
    public static class responsePage{
        private Long dbid;
        private Long boardId;
        private String title;
        private String major;
        private String type;
        private String writer;
        private LocalDate regdate;
        private Long view;
        private Long likeCount;
        private boolean checkLike;

        public responsePage(LikeService likeS, Notice notice) {
            this.dbid = notice.getId();
            this.boardId = notice.getBoardId();
            this.title = notice.getTitle();
            this.major = notice.getMajor();
            this.type = notice.getType();
            this.writer = notice.getWriter();
            this.regdate = notice.getRegdate();
            this.view = notice.getView();
            this.likeCount = notice.getLikeCount();
            this.checkLike = likeS.checkedLike(this.dbid);
        }
    }

    @Getter
    public static class requestbody{
        private Long dbid;
    }

    @Getter
    public static class responsebody{
        private Long dbid;
        private Long boardId;
        private String title;
        private String major;
        private String type;
        private String writer;
        private LocalDate regdate;
        private Long view;
        private Long likeCount;
        private boolean checkLike;

        private String body;
        private String img;
        private List<CommentDTO.Comment> comments;

        public responsebody(LikeService likeS, Notice notice) {
            this.dbid = notice.getId();
            this.boardId = notice.getBoardId();
            this.title = notice.getTitle();
            this.major = notice.getMajor();
            this.type = notice.getType();
            this.writer = notice.getWriter();
            this.regdate = notice.getRegdate();
            this.view = notice.getView();
            this.likeCount = notice.getLikeCount();
            this.checkLike = likeS.checkedLike(this.dbid);

            this.body = notice.getBody();
            this.img = notice.getImg();
            this.comments = notice.getComments().stream().map(CommentDTO.Comment::new).collect(Collectors.toList());
        }
    }
}