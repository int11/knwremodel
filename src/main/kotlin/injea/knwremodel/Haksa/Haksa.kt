package injea.knwremodel.Haksa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Haksa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var date_start: String? = null
        private set
    var date_end: String? = null
        private set
    var schedule: String? = null

    fun setDateStart(dateStart: String?) {
        this.date_start = dateStart
    }

    fun setDateEnd(dateEnd: String?) {
        this.date_end = dateEnd
    }

    override fun toString(): String {
        return "Haksa(id=" + this.id + ", date_start=" + this.date_start + ", date_end=" + this.date_end + ", schedule=" + this.schedule + ")"
    }
}
