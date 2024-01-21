package injea.knwremodel.College;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "colleges")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String url;

    @Builder
    public College(Long id, String college, String major, String url) {
        this.id = id;
        this.college = college;
        this.major = major;
        this.url = url;
    }
}

