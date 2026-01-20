package com.careers.CareerHub.dto;

import com.careers.CareerHub.entity.User;
import lombok.Data;

import java.time.Instant;

@Data
public class ResumeDto {
    private Long id;
    private String title;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
    private User user;
    private Instant uploadedAt;
}

