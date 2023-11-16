package com.kn.knwremodel.repository;

import com.kn.knwremodel.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
