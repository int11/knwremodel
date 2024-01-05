package com.kn.knwremodel.Haksa;

import java.time.Year;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "haksayear")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Haksayear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Year year;
}
