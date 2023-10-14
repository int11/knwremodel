package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.Like;
import com.kn.knwremodel.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Boolean existsByUserAndNotice(String loginId, Notice notice);
    Like findByUserAndNotice(String user, Notice notice);

}