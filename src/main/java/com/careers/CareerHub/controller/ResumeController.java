package com.careers.CareerHub.controller;

import com.careers.CareerHub.entity.Resume;
import com.careers.CareerHub.repository.ResumeRepository;
import com.careers.CareerHub.security.CustomUserDetails;
import com.careers.CareerHub.service.ResumeService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title
    ) throws IOException {
        return ResponseEntity.ok(
                resumeService.uploadResume(file,title,user.getUsername())
        );
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<?> getMyResume(
            @AuthenticationPrincipal CustomUserDetails user
    ){
        return ResponseEntity.ok(
                resumeService.getMyResume(user.getUsername())
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadResume(
            @AuthenticationPrincipal CustomUserDetails user
    ) throws IOException{
        Resume resume = resumeService.getMyResume(user.getUsername());
        Path path = Paths.get(resume.getFileUrl());

        Resource resource = new UrlResponse(path.toUri());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + path.getFileName() + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);


    }

}
