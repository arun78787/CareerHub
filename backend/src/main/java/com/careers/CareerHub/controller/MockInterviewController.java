package com.careers.CareerHub.controller;

import com.careers.CareerHub.entity.MockInterview;
import com.careers.CareerHub.service.MockInterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mock-interviews")
@RequiredArgsConstructor
public class MockInterviewController {
    private final MockInterviewService service;

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody Map<String,Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        Long jobId = body.containsKey("jobId") ? Long.valueOf(body.get("jobId").toString()) : null;
        String roleName = (String) body.getOrDefault("roleName", "Backend Engineer");
        String resumeText = (String) body.getOrDefault("resumeText", "");
        String jobDesc = (String) body.getOrDefault("jobDesc", "");
        MockInterview mi = service.generateForJob(userId, jobId, resumeText, roleName, jobDesc);
        return ResponseEntity.ok(mi);
    }
}
