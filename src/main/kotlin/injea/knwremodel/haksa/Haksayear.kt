package injea.knwremodel.haksa

import jakarta.persistence.*
import java.time.Year

@Table(name = "haksayear")
@Entity
class Haksayear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    var year: Year? = null
        private set

    constructor(id: Long?, year: Year?) {
        this.id = id
        this.year = year
    }

    protected constructor()

    override fun toString(): String {
        return "Haksayear(id=" + this.id + ", year=" + this.year + ")"
    }

    class HaksayearBuilder internal constructor() {
        private var id: Long? = null
        private var year: Year? = null
        fun id(id: Long?): HaksayearBuilder {
            this.id = id
            return this
        }

        fun year(year: Year?): HaksayearBuilder {
            this.year = year
            return this
        }

        fun build(): Haksayear {
            return Haksayear(this.id, this.year)
        }

        override fun toString(): String {
            return "Haksayear.HaksayearBuilder(id=" + this.id + ", year=" + this.year + ")"
        }
    }

    companion object {
        fun builder(): HaksayearBuilder {
            return HaksayearBuilder()
        }
    }
}
