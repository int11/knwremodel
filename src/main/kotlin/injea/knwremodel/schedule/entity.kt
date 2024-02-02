package injea.knwremodel.schedule

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Schedule(
    dateStart: LocalDate,
    dateEnd: LocalDate,
    text: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long? = null

    var dateStart = dateStart
        protected set
    var dateEnd = dateEnd
        protected set
    var text = text
        protected set
}
