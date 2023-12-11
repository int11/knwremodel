package com.kn.knwremodel.repository;


import com.kn.knwremodel.entity.Notice;
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
    List<Notice> findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(@Param("major") String major, @Param("type") String type, @Param("keyword") String keyword);
    List<Notice> findByMajorContainingAndTypeContainingAndTitleContaining(String major, String type, String keyword, Sort sort);

    boolean existsByBoardId(Long board_id);

    boolean existsByMajor(String major);

    List<Notice> findByMajorAndRegdate(String major, LocalDate localDate);

    @Query("SELECT MAX(n.boardId) FROM Notice n WHERE n.major = :major")
    int findMaxBoardIdByMajor(@Param("major") String major);

    @Query("SELECT n FROM Notice n WHERE n.regdate >= :beforeDate")
    List<Notice> findByDescWhereByRegDate(@Param("beforeDate") LocalDate beforeDate, Pageable pageable);

    List<Notice> findByMajorContaining(String major, Pageable pageable);
    long count();
}