package injea.knwremodel.Notice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import injea.knwremodel.Comment.CommentDTO;
import injea.knwremodel.Like.LikeService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class NoticeDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
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
        private LocalDateTime regdate;
        private Long view;
        private Long likeCount;
        private boolean checkLike;
        private String img;

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
            this.img = notice.getImg();

        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
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
        private LocalDateTime regdate;
        private Long view;
        private Long likeCount;
        private boolean checkLike;

        private String body;
        private String img;
        private String html;
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
            this.html = notice.getHtml();
            this.comments = notice.getComments().stream().map(CommentDTO.Comment::new).collect(Collectors.toList());
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class toplike{
        private String major;
        private int size;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class topview{
        private int size;
    }
}