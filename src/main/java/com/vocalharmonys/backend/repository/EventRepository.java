package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Event;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate from);

    List<Event> findByEventDateLessThanOrderByEventDateDesc(LocalDate before);

    List<Event> findAllByOrderByEventDateAsc();
}
