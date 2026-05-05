package com.campus.eventmanagement.dto;

import com.campus.eventmanagement.model.EventType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    private String description;

    @NotNull(message = "Event type is required")
    private EventType eventType;

    private String department;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventDate;

    private String venue;

    @Positive(message = "Capacity must be positive")
    private Integer maxCapacity;
}
