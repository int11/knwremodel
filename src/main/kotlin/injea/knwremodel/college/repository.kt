package injea.knwremodel.college

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CollegeRepository : JpaRepository<College?, Long?> {
    @Query("SELECT e.major FROM College e")
    fun findAllMajor(): List<String>
}
