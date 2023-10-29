package com.kn.knwremodel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "likes") //이거 이름 안바꿔 주면 충돌남 SQL 예약어라서..
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;
}