package com.campus.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationConfirmation(String email, String studentName, String eventTitle, String eventDate, String venue) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Event Registration Confirmed - " + eventTitle);
        message.setText("Dear " + studentName + ",\n\nYou have successfully registered for " + eventTitle +
                " on " + eventDate + " at " + venue + ".\nWe look forward to seeing you!");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email failed to send: " + e.getMessage());
        }
    }

    public void sendEventCancellation(String email, String studentName, String eventTitle, String eventDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Event Cancelled - " + eventTitle);
        message.setText("Dear " + studentName + ",\n\nWe regret to inform you that " + eventTitle +
                " scheduled for " + eventDate + " has been cancelled.");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email failed to send: " + e.getMessage());
        }
    }

    public void sendFeedbackRequest(String email, String studentName, String eventTitle, String feedbackLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Share Your Feedback - " + eventTitle);
        message.setText("Dear " + studentName + ",\n\nThank you for attending " + eventTitle +
                ".\n\nPlease share your feedback at: " + feedbackLink);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email failed to send: " + e.getMessage());
        }
    }
}
