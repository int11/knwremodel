package injea.knwremodel.schedule

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface repository : JpaRepository<Schedule?, Long?>
