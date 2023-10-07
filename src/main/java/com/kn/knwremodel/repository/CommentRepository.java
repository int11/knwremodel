package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.Comment;
import com.kn.knwremodel.entity.Notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}

