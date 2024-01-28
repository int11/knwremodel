package injea.knwremodel.haksa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HaksayearRepository : JpaRepository<Haksayear?, Long?>

