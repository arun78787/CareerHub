package com.careers.CareerHub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class JobOpening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;
    private String region;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String skills; //JSON string array e.g.

    private String sourceUrl;

    @CreationTimestamp
    private Instant postedAt;

    private String embeddingKey; //optional refernce to vector store
}
