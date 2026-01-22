package com.careers.CareerHub.controller;

import com.careers.CareerHub.security.CustomUserDetails;
import com.careers.CareerHub.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/schedule")
    public ResponseEntity<?> schedule(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam Instant scheduledAt,
            @RequestParam String interviewerName
    ) {
        return ResponseEntity.ok(
                interviewService.scheduleInterview(
                        scheduledAt,
                        interviewerName,
                        user.getUsername()
                )
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<?> myInterviews(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                interviewService.myInterviews(user.getUsername())
        );
    }
}
