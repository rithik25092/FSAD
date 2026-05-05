package com.campus.eventmanagement.service;

import com.campus.eventmanagement.model.Event;
import com.campus.eventmanagement.model.Feedback;
import com.campus.eventmanagement.model.User;
import com.campus.eventmanagement.repository.EventRepository;
import com.campus.eventmanagement.repository.FeedbackRepository;
import com.campus.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Feedback submitFeedback(Long studentId, Long eventId, Integer rating, String comments) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Feedback feedback = new Feedback();
        feedback.setStudent(student);
        feedback.setEvent(event);
        feedback.setRating(rating);
        feedback.setComments(comments);
        
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByEvent(Long eventId) {
        return feedbackRepository.findByEventId(eventId);
    }

    public Double getAverageRating(Long eventId) {
        Double avg = feedbackRepository.getAverageRatingForEvent(eventId);
        return avg != null ? avg : 0.0;
    }
}
