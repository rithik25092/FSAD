package com.campus.eventmanagement.controller;

import com.campus.eventmanagement.dto.EventStatsDTO;
import com.campus.eventmanagement.model.Event;
import com.campus.eventmanagement.model.EventType;
import com.campus.eventmanagement.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<EventStatsDTO> stats = eventService.getEventStatistics();
        model.addAttribute("totalEvents", stats.size());
        model.addAttribute("stats", stats.stream().limit(5).toList());
        return "admin/dashboard";
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.filterEvents(null, null, null, null));
        return "admin/events";
    }

    @GetMapping("/events/new")
    public String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("eventTypes", EventType.values());
        return "admin/event-form";
    }

    @PostMapping("/events")
    public String createEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("eventTypes", EventType.values());
            return "admin/event-form";
        }
        eventService.createEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/events/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        model.addAttribute("eventTypes", EventType.values());
        return "admin/event-form";
    }

    @PutMapping("/events/{id}")
    public String updateEvent(@PathVariable Long id, @Valid @ModelAttribute("event") Event event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("eventTypes", EventType.values());
            return "admin/event-form";
        }
        eventService.updateEvent(id, event);
        return "redirect:/admin/events";
    }

    @DeleteMapping("/events/{id}")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        eventService.deleteEvent(id);
        redirectAttributes.addFlashAttribute("successMsg", "Event deleted successfully");
        return "redirect:/admin/events";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("stats", eventService.getEventStatistics());
        return "admin/statistics";
    }
}
