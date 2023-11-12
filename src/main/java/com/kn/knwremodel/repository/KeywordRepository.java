package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByKeyword(String keyword);
    List<Keyword> findTop5ByOrderByCountsDesc();
}