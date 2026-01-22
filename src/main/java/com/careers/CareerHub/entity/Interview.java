package com.careers.CareerHub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant scheduledAt;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    private String interviewerName;

    private String meetingLink; // stubbed for now

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Instant createdAt = Instant.now();
}
