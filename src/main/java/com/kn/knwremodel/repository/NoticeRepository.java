package com.kn.knwremodel.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kn.knwremodel.entity.Notice;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n WHERE n.major NOT LIKE '행사/안내' AND n.major LIKE %:major AND n.type LIKE %:type AND n.title LIKE CONCAT ('%', :keyword, '%')")
    List<Notice> findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(@Param("major") String major, @Param("type") String type, @Param("keyword") String keyword);

    List<Notice> findByMajorContainingAndTypeContainingAndTitleContainingOrderByBoardIdDesc(String major, String type, String title);

    boolean existsByBoardId(Long board_id);

    boolean existsByMajor(String major);

    List<Notice> findByMajor(String major);

    @Query("SELECT MAX(n.boardId) FROM Notice n WHERE n.major = :major")
    int findMaxBoardIdByMajor(@Param("major") String major);

    @Query("SELECT n FROM Notice n WHERE n.regdate >= :beforeDate")
    List<Notice> findByDescWhereByRegDate(@Param("beforeDate") LocalDate beforeDate, Pageable pageable);

    List<Notice> findByMajor(String major, Pageable pageable);
    long count();
}