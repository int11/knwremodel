package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.Haksa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HaksaRepository extends JpaRepository<Haksa, Long> {

}
