package com.careers.CareerHub.dto;

import com.careers.CareerHub.entity.InterviewStatus;
import com.careers.CareerHub.entity.User;
import lombok.Data;

import java.time.Instant;

@Data
public class InterviewDto {
    private Long id;
    private String company;
    private String role;
    private Instant scheduledAt;
    private Integer durationMinutes;
//    private User organizer;
//    private User candidate;
    private InterviewStatus status;
}
