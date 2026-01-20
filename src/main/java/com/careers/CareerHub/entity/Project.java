package com.careers.CareerHub.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private String githubUrl;
    private String liveDemoUrl;

    private String techStack;   //springboot , React, mySql
    @ManyToOne
    private User owner;

    private boolean isPublic;

    @CreationTimestamp
    private Instant createdAt;

}
