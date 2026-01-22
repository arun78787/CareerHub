package com.careers.CareerHub.controller;

import com.careers.CareerHub.dto.ProjectDto;
import com.careers.CareerHub.service.JwtService;
import com.careers.CareerHub.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestHeader("Authorization") String auth,
            @RequestBody ProjectDto dto
    ){
        String token = auth.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                projectService.create(dto,email)
        );
    }
    @GetMapping
    public ResponseEntity<?> myProjects(
            @RequestHeader("Authorization") String auth
    ) {
        String token = auth.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                projectService.getUserProjects(email)
        );
    }
}
