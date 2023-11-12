package com.kn.knwremodel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyword;
    @Column(nullable = false)
    private Long counts;
    public void updateKeywordCount(Long counts) {
        this.counts = counts;
    }

}
