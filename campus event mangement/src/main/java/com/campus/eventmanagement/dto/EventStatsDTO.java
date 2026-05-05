package com.campus.eventmanagement.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventStatsDTO {
    private Long eventId;
    private String title;
    private Integer maxCapacity;
    private Long currentRegistrations;
    private Double fillPercentage;
    private Double averageRating;
}
