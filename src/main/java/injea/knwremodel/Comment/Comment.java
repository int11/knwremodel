package injea.knwremodel.Comment;

import injea.knwremodel.Notice.Notice;
import injea.knwremodel.User.User;
import injea.knwremodel.entity.TimeEntity;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Comment extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 작성자

    @Setter
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment; // 댓글 내용
    
    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;
}