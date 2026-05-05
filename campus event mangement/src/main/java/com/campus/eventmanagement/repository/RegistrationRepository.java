package com.campus.eventmanagement.repository;

import com.campus.eventmanagement.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndEventId(Long studentId, Long eventId);
    
    @Query("SELECT r FROM Registration r JOIN FETCH r.event e WHERE r.student.id = :studentId")
    List<Registration> findByStudentId(@Param("studentId") Long studentId);
    
    long countByEventId(Long eventId);
    Optional<Registration> findByStudentIdAndEventId(Long studentId, Long eventId);
}
