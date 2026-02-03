package com.careers.CareerHub.controller;

import com.careers.CareerHub.entity.Resume;
import com.careers.CareerHub.security.CustomUserDetails;
import com.careers.CareerHub.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Map;

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
    ) throws Exception { // reflect service exception
        return ResponseEntity.ok(
                resumeService.uploadResume(file, title, user.getUsername())
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
    public ResponseEntity<?> downloadResume(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Resume resume = resumeService.getMyResume(user.getUsername());
        URL url = resumeService.getSignedUrl(resume.getFileUrl());
        return ResponseEntity.ok(Map.of("url", url.toString()));
    }

    public ResponseEntity<?> getMyResumeAiReview(@AuthenticationPrincipal CustomUserDetails user){
        Resume resume = resumeService.getMyResume(user.getUsername());
        return ResponseEntity.ok(Map.of(
                "aiScore", resume.getAiScore(),
                "aiReview", resume.getAiReview(),
                "parsedSkills", resume.getParsedSkills()
        ));
    }
}
