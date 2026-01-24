package com.careers.CareerHub.dto;

import com.careers.CareerHub.entity.Project;
import com.careers.CareerHub.entity.Resume;

import java.util.List;

public record PublicPortfolioDto(
        String name,
        String email,
        List<Project> projects,
        Resume resume
) { }
