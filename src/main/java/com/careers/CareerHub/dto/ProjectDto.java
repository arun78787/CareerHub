package com.careers.CareerHub.dto;

import com.careers.CareerHub.entity.User;
import lombok.Data;

import java.time.Instant;

@Data
public class ProjectDto {
    private Long id;
    private String title;
    private String description;
    private String githubUrl;
    private String liveDemoUrl;
    private String techStack;
    private User owner;
    private boolean isPublic;
    private Instant createdAt;
}
