package com.careers.CareerHub.controller;

import com.careers.CareerHub.dto.ProjectDto;
import com.careers.CareerHub.security.CustomUserDetails;
import com.careers.CareerHub.service.JwtService;
import com.careers.CareerHub.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ProjectDto dto
    ) {
        return ResponseEntity.ok(
                projectService.create(dto, userDetails.getUsername())
        );
    }

    @GetMapping
    public ResponseEntity<?> myProjects(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                projectService.getUserProjects(userDetails.getUsername())
        );
    }
}

