package injea.knwremodel.Haksa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HaksaRepository : JpaRepository<Haksa?, Long?>
