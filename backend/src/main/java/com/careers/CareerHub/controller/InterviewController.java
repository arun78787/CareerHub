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
            @RequestParam String scheduledAt,    // accept string from client
            @RequestParam String interviewerName
    ) {
        // parse ISO string; will throw DateTimeParseException if bad
        Instant scheduled;
        try {
            scheduled = Instant.parse(scheduledAt);
        } catch (Exception ex) {
            // If client sends local datetime like "2026-01-30T10:00", try to parse as local and convert:
            scheduled = Instant.parse(scheduledAt + "Z"); // fallback - better to send timezone from client
        }

        return ResponseEntity.ok(
                interviewService.scheduleInterview(
                        scheduled,
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
