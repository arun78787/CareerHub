package com.careers.CareerHub.controller;

import com.careers.CareerHub.dto.ProjectDto;
import com.careers.CareerHub.security.CustomUserDetails;
import com.careers.CareerHub.service.JwtService;
import com.careers.CareerHub.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createProject(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody ProjectDto dto
    ) {
        return ResponseEntity.ok(
                projectService.create(dto, user.getUsername())
        );
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<?> myProjects(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue =  "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String dir
    ) {
        return ResponseEntity.ok(
                projectService.getUserProjects(user.getUsername(),page, size,sortBy,dir)
        );
    }
    //ADMIN only side
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> allProjects(){
        return ResponseEntity.ok("Admin can see all projects");
    }
}

