package com.kn.knwremodel.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.kn.knwremodel.entity.Notice;

import lombok.Getter;


public class NoticeDTO {
    @Getter
    public static class requestPage{
        private String major;
        private String type;
        private String title;
        private Long count;
        private Long page;
    }

    @Getter
    public static class responsePage{
        private Long dbid;
        private Long boardId;
        private String title;
        private String writer;
        private LocalDate regdate;
        private int view;

        public responsePage(Notice notice) {
            this.dbid = notice.getId();
            this.boardId = notice.getBoardId();
            this.title = notice.getTitle();
            this.writer = notice.getWriter();
            this.regdate = notice.getRegdate();
            this.view = notice.getView();
        }
    }

    @Getter
    public static class requestbody{
        private Long dbid;
    }

    @Getter
    public static class responsebody{
        private String body;
        private String img;
        private List<CommentDTO.Comment> comments;
        public responsebody(Notice notice) {
            this.body = notice.getBody();
            this.img = notice.getImg();
            this.comments = notice.getComments().stream().map(CommentDTO.Comment::new).collect(Collectors.toList());
        }
    }
}