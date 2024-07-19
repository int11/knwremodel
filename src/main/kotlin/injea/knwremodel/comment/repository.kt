package injea.knwremodel.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment?, Long?> {
    @Query("SELECT c FROM Comment c WHERE c.notice.id = :noticeId")
    fun findByNoticeId(@Param("noticeId") noticeId: Long): MutableList<Comment>
}

