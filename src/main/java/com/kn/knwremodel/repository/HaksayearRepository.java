package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.Haksayear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HaksayearRepository extends JpaRepository<Haksayear, Long> {
    
}

