package com.campus.eventmanagement.dto;

import com.campus.eventmanagement.model.RegistrationStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegistrationDTO {
    private Long id;
    private Long eventId;
    private String eventTitle;
    private LocalDateTime eventDate;
    private String venue;
    private LocalDateTime registeredAt;
    private RegistrationStatus status;
}
