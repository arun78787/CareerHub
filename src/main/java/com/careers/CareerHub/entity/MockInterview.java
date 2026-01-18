package com.careers.CareerHub.entity;

import jakarta.persistence.*;

@Entity
public class MockInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoUrl;
    private Integer score;

    @OneToOne
    private Interview interview;

    private String feedback;
}
