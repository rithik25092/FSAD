package com.campus.eventmanagement.controller;

import com.campus.eventmanagement.exception.AlreadyRegisteredException;
import com.campus.eventmanagement.exception.EventNotFoundException;
import com.campus.eventmanagement.exception.RegistrationFullException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public String handleEventNotFound(EventNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(RegistrationFullException.class)
    public String handleRegistrationFull(RegistrationFullException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
        return "redirect:/student/events";
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public String handleAlreadyRegistered(AlreadyRegisteredException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("warningMsg", ex.getMessage());
        return "redirect:/student/events";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return "error/500";
    }
}
