package com.campus.eventmanagement.service;

import com.campus.eventmanagement.dto.EventStatsDTO;
import com.campus.eventmanagement.exception.EventNotFoundException;
import com.campus.eventmanagement.model.Event;
import com.campus.eventmanagement.model.EventType;
import com.campus.eventmanagement.repository.EventRepository;
import com.campus.eventmanagement.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event existing = getEventById(id);
        existing.setTitle(updatedEvent.getTitle());
        existing.setDescription(updatedEvent.getDescription());
        existing.setEventType(updatedEvent.getEventType());
        existing.setDepartment(updatedEvent.getDepartment());
        existing.setEventDate(updatedEvent.getEventDate());
        existing.setVenue(updatedEvent.getVenue());
        existing.setMaxCapacity(updatedEvent.getMaxCapacity());
        return eventRepository.save(existing);
    }

    @Transactional
    public void deleteEvent(Long id) {
        if(!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
    }

    public List<Event> getAllUpcomingEvents() {
        return eventRepository.findByEventDateAfterOrderByEventDateAsc(LocalDateTime.now());
    }

    public List<Event> filterEvents(EventType type, String department, LocalDateTime start, LocalDateTime end) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .filter(e -> (type == null || e.getEventType() == type))
                .filter(e -> (department == null || department.isEmpty() || e.getDepartment().equalsIgnoreCase(department)))
                .filter(e -> (start == null || !e.getEventDate().isBefore(start)))
                .filter(e -> (end == null || !e.getEventDate().isAfter(end)))
                .collect(Collectors.toList());
    }

    public List<EventStatsDTO> getEventStatistics() {
        List<Object[]> stats = eventRepository.findEventsWithRegistrationCount();
        List<EventStatsDTO> statList = new ArrayList<>();
        
        for (Object[] row : stats) {
            Event event = (Event) row[0];
            Long count = (Long) row[1];
            Double fillRate = event.getMaxCapacity() != null && event.getMaxCapacity() > 0 
                ? ((double) count / event.getMaxCapacity()) * 100 : 0.0;
            Double avgRating = 0.0; // Handled by controller explicitly
            statList.add(new EventStatsDTO(event.getId(), event.getTitle(), event.getMaxCapacity(), count, fillRate, avgRating));
        }
        return statList;
    }
}
