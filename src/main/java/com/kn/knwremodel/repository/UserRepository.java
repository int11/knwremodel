package com.kn.knwremodel.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kn.knwremodel.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<User> findByEmail(String email);

}
