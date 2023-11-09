package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.College;
import com.kn.knwremodel.entity.Notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    @Query("SELECT e.major FROM College e")
    List<String> findAllMajor();
}
