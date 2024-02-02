package injea.knwremodel.like

import injea.knwremodel.notice.Notice
import injea.knwremodel.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : JpaRepository<Like?, Long?> {
    fun existsByUserAndNotice(user: User, notice: Notice): Boolean
    fun findByUserAndNotice(user: User, notice: Notice): Like?
}