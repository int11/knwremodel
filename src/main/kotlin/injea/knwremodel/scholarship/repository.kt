package injea.knwremodel.scholarship

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ScholarshipRepository : JpaRepository<Scholarship?, Long?> {
    fun existsByRegdate(localDate: LocalDate?): Boolean
}