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

    //User 부분 임의 구성
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "MEMBER_ID")
    @Column(nullable = false)
    private String user;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;
}