package com.campus.eventmanagement.controller;

import com.campus.eventmanagement.config.CustomUserDetails;
import com.campus.eventmanagement.dto.FeedbackDTO;
import com.campus.eventmanagement.model.Event;
import com.campus.eventmanagement.model.EventType;
import com.campus.eventmanagement.model.Registration;
import com.campus.eventmanagement.model.User;
import com.campus.eventmanagement.service.EventService;
import com.campus.eventmanagement.service.FeedbackService;
import com.campus.eventmanagement.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        List<Event> upcomingEvents = eventService.getAllUpcomingEvents();
        List<Registration> myRegistrations = registrationService.getStudentRegistrations(user.getId());
        
        model.addAttribute("upcomingCount", upcomingEvents.size());
        model.addAttribute("registeredCount", myRegistrations.size());
        model.addAttribute("recentEvents", upcomingEvents.stream().limit(5).collect(Collectors.toList()));
        return "student/dashboard";
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllUpcomingEvents());
        model.addAttribute("eventTypes", EventType.values());
        return "student/events";
    }

    @GetMapping("/events/{id}")
    public String eventDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Event event = eventService.getEventById(id);
        List<Registration> userRegs = registrationService.getStudentRegistrations(userDetails.getUser().getId());
        boolean isRegistered = userRegs.stream().anyMatch(r -> r.getEvent().getId().equals(id));
        
        model.addAttribute("event", event);
        model.addAttribute("isRegistered", isRegistered);
        model.addAttribute("avgRating", feedbackService.getAverageRating(id));
        
        if (isRegistered && event.getEventDate().isBefore(java.time.LocalDateTime.now())) {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setEventId(id);
            model.addAttribute("feedback", feedbackDTO);
            model.addAttribute("canFeedback", true);
        } else {
            model.addAttribute("canFeedback", false);
        }
        return "student/event-detail";
    }

    @PostMapping("/events/{id}/register")
    public String registerForEvent(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        try {
            registrationService.registerStudent(userDetails.getUser().getId(), id);
            redirectAttributes.addFlashAttribute("successMsg", "Successfully registered for event.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/student/events/" + id;
    }

    @GetMapping("/my-registrations")
    public String myRegistrations(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("registrations", registrationService.getStudentRegistrations(userDetails.getUser().getId()));
        return "student/my-registrations";
    }

    @PostMapping("/feedback/{eventId}")
    public String submitFeedback(@PathVariable Long eventId, @Valid @ModelAttribute("feedback") FeedbackDTO feedbackDTO, 
                                 BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDetails, 
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Invalid feedback data");
            return "redirect:/student/events/" + eventId;
        }
        try {
            feedbackService.submitFeedback(userDetails.getUser().getId(), eventId, feedbackDTO.getRating(), feedbackDTO.getComments());
            redirectAttributes.addFlashAttribute("successMsg", "Feedback submitted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/student/events/" + eventId;
    }
}
