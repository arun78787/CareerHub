package com.careers.CareerHub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;    //image/png
    private String url;     //s3/local path
    private Long size;


    @CreationTimestamp
    private Instant uploadedAt;

}
