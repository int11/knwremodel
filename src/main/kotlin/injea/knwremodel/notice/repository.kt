package injea.knwremodel.notice

import injea.knwremodel.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/* list, pages는 size 0일 경우 null 이 아닌 size 0인 객체가 반환됨*/
@Repository
interface NoticeRepository : JpaRepository<Notice?, Long?> {
    @Query("SELECT n FROM Notice n WHERE n.major LIKE %:major AND n.type LIKE %:type AND n.title LIKE CONCAT ('%', :keyword, '%')")
    fun search(@Param("major") major: String, @Param("type") type: String, @Param("keyword") keyword: String, pageable: Pageable): Page<Notice>

    @Query("SELECT n FROM Notice n WHERE n.major NOT LIKE '행사/안내' AND n.major LIKE %:major AND n.type LIKE %:type AND n.title LIKE CONCAT ('%', :keyword, '%')")
    fun searchWithout(@Param("major") major: String, @Param("type") type: String, @Param("keyword") keyword: String, pageable: Pageable): Page<Notice>

    fun existsByMajorAndBoardId(major: String, board_id: Long): Boolean
    fun existsByTitle(title: String): Boolean

    @Query("SELECT n FROM Notice n WHERE n.regdate >= :beforeDate")
    fun findByRegdateGreaterThanEqual(@Param("beforeDate") beforeDate: LocalDateTime, pageable: Pageable): MutableList<Notice>

    @Query("SELECT n FROM Notice n WHERE n.major LIKE %:major AND n.likeCount > 0")
    fun findByMajorContaining(@Param("major") major: String, pageable: Pageable): MutableList<Notice>
    override fun count(): Long

    @Query("SELECT n FROM Notice n JOIN n.likes l WHERE l.user = :user")
    fun findByUserLikes0(@Param("user") user: User): MutableList<Notice>
}