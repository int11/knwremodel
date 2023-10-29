package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.Like;
import com.kn.knwremodel.entity.Notice;
import com.kn.knwremodel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Boolean existsByUserAndNotice(User user, Notice notice);
    Like findByUserAndNotice(User user, Notice notice);

}