package com.kn.knwremodel.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "scholarships")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private LocalDate regdate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Setter
    private List<String> columns = new ArrayList<>();

    @Builder
    public Scholarship(LocalDate regdate) {
        this.regdate = regdate;
        for (int i = 0; i < 12; i++){
            this.columns.add("");
        }
    }
}