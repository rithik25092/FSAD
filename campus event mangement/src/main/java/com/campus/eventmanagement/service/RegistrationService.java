package com.campus.eventmanagement.service;

import com.campus.eventmanagement.exception.AlreadyRegisteredException;
import com.campus.eventmanagement.exception.EventNotFoundException;
import com.campus.eventmanagement.exception.RegistrationFullException;
import com.campus.eventmanagement.model.Event;
import com.campus.eventmanagement.model.Registration;
import com.campus.eventmanagement.model.RegistrationStatus;
import com.campus.eventmanagement.model.User;
import com.campus.eventmanagement.repository.EventRepository;
import com.campus.eventmanagement.repository.RegistrationRepository;
import com.campus.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Registration registerStudent(Long studentId, Long eventId) {
        if (registrationRepository.existsByStudentIdAndEventId(studentId, eventId)) {
            throw new AlreadyRegisteredException("You are already registered for this event.");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (event.getCurrentRegistrations() >= event.getMaxCapacity()) {
            throw new RegistrationFullException("Registration is full for this event.");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setEvent(event);
        registration.setStatus(RegistrationStatus.CONFIRMED);

        event.setCurrentRegistrations(event.getCurrentRegistrations() + 1);
        eventRepository.save(event);

        Registration saved = registrationRepository.save(registration);

        emailService.sendRegistrationConfirmation(student.getEmail(), student.getName(), event.getTitle(), 
                event.getEventDate().toString(), event.getVenue());

        return saved;
    }

    @Transactional
    public void cancelRegistration(Long registrationId) {
        Registration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
        
        if (reg.getStatus() == RegistrationStatus.CANCELLED) {
            return;
        }

        reg.setStatus(RegistrationStatus.CANCELLED);
        Event event = reg.getEvent();
        event.setCurrentRegistrations(event.getCurrentRegistrations() - 1);
        eventRepository.save(event);
        registrationRepository.save(reg);

        User student = reg.getStudent();
        emailService.sendEventCancellation(student.getEmail(), student.getName(), event.getTitle(), event.getEventDate().toString());
    }

    public List<Registration> getStudentRegistrations(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }
}
