package com.kn.knwremodel.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "scholarships") //이거 이름 안바꿔 주면 충돌남 SQL 예약어라서..
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String food;
}