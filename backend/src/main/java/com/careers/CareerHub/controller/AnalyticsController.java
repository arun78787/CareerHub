package com.careers.CareerHub.controller;

import com.careers.CareerHub.security.CustomUserDetails;
import com.careers.CareerHub.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/interviews")
    public ResponseEntity<?> interviewStats(
            @AuthenticationPrincipal CustomUserDetails user
            ){
        return ResponseEntity.ok(
                analyticsService.interviewAnalytics(user.getUsername())
        );
    }
}
