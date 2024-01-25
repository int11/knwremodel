package injea.knwremodel.Notice;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n WHERE n.major NOT LIKE '행사/안내' AND n.major LIKE %:major AND n.type LIKE %:type AND n.title LIKE CONCAT ('%', :keyword, '%') ORDER BY n.boardId DESC ")
    Page<Notice> findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(@Param("major") String major, @Param("type") String type, @Param("keyword") String keyword, Pageable pageable);
    Page<Notice> findByMajorContainingAndTypeContainingAndTitleContaining(String major, String type, String keyword, Pageable pageable);
    boolean existsByMajorAndBoardId(String major, Long board_id);
    boolean existsByTitle(String title);

    @Query("SELECT n FROM Notice n WHERE n.regdate >= :beforeDate")
    List<Notice> findByDescWhereByRegDate(@Param("beforeDate") LocalDate beforeDate, Pageable pageable);

    List<Notice> findByMajorContaining(String major, Pageable pageable);
    long count();
}