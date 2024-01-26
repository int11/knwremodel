package injea.knwremodel.Like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import injea.knwremodel.notice.Notice;
import injea.knwremodel.User.User;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "likes") //이거 이름 안바꿔 주면 충돌남 SQL 예약어라서..
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;
}