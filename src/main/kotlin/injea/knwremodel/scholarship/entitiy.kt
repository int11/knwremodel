package injea.knwremodel.scholarship

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name="scholarships")
class Scholarship(regdate: LocalDate){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long? = null

    var regdate = regdate
        protected set

    @ElementCollection(fetch = FetchType.EAGER)
    var columns: MutableList<String> = mutableListOf()

    init {
        for (i in 0..11) {
            columns.add("")
        }
    }
}