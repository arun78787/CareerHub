package com.careers.CareerHub.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;   //backend Resume 2025
    private String fileUrl;     //S3 / local
    private String fileType;    //pdf / docx
    private Long fileSize;
    @OneToOne
    private User user;

    @CreationTimestamp
    private Instant uploadedAt;

}
