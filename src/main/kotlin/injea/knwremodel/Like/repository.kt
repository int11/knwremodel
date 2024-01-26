package injea.knwremodel.Like

import injea.knwremodel.User.User
import injea.knwremodel.notice.Notice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : JpaRepository<Like?, Long?> {
    fun existsByUserAndNotice(user: User?, notice: Notice?): Boolean

    fun findByUserAndNotice(user: User?, notice: Notice?): Like?

    @Query("SELECT DISTINCT n FROM Notice n JOIN n.likes l WHERE l.user.id = :userId")
    fun findLikedNoticesByUser(@Param("userId") userId: Long?): List<Notice?>?
}