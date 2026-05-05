package com.campus.eventmanagement.repository;

import com.campus.eventmanagement.model.Event;
import com.campus.eventmanagement.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventType(EventType type);
    List<Event> findByDepartment(String dept);
    List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByEventDateAfterOrderByEventDateAsc(LocalDateTime date);

    @Query("SELECT e, COUNT(r) FROM Event e LEFT JOIN e.registrations r GROUP BY e")
    List<Object[]> findEventsWithRegistrationCount();
}
