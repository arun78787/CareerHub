package com.careers.CareerHub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
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

    /**
     * Optional: store parsed skills as a JSON string (e.g. ["Spring Boot","MySQL"])
     * For now we'll store JSON text. You can change to a JSON column later.
     */
    @Column(columnDefinition = "TEXT")
    private String parsedSkills;

    //full AI review JSON(stringified) returned by LLM
    @Column(columnDefinition = "LONGTEXT")
    private String aiReview;

    //simple numeric score 0-100 suggested by AI review
    private Integer aiScore;

    @CreationTimestamp
    private Instant uploadedAt;

}
