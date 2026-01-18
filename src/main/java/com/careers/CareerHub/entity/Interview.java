package com.careers.CareerHub.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String role;

    private Instant scheduledAt;
    private Integer durationMinutes;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

}
