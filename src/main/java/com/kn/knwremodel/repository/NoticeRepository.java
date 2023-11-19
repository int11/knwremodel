package com.kn.knwremodel.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kn.knwremodel.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n WHERE n.major NOT LIKE '행사/안내' AND n.major LIKE %:major AND n.type LIKE %:type AND n.title LIKE CONCAT ('%', :keyword, '%')")
    List<Notice> findByMajorExceptEventContainingAndTypeContainingAndTitleContaining(@Param("major") String major, @Param("type") String type, @Param("keyword") String keyword);

    List<Notice> findByMajorContainingAndTypeContainingAndTitleContaining(String major,String type, String title);
    boolean existsByBoardId(Long board_id);
    boolean existsByMajor(String major);

    @Query("SELECT MAX(n.boardId) FROM Notice n WHERE n.major = :major")
    int findMaxBoardIdByMajor(@Param("major") String major);

    @Query("SELECT n FROM Notice n WHERE n.regdate >= :beforeDate AND n.regdate <= :nowDate")
    List<Notice> findByOrderByViewDescWhereByRegDate(@Param("beforeDate") LocalDate beforeDate, @Param("nowDate") LocalDate nowDate, Pageable pageable);
    List<Notice> findTop3ByMajorOrderByLikeCountDesc(String major);
    long count();
}