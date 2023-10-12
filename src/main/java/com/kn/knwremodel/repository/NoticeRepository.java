package com.kn.knwremodel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kn.knwremodel.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByMajorContaining(String major);
    List<Notice> findByMajor(String major);
    List<Notice> findByType(String type);
    boolean existsByBoardId(Long board_id);
    boolean existsByMajor(String major);

    @Query("SELECT MAX(n.boardId) FROM Notice n WHERE n.major = :major")
    int findMaxBoardIdByMajor(@Param("major") String major);

    List<Notice> findByMajorAndType(String major, String type);
}