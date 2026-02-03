package com.careers.CareerHub.controller;

import com.careers.CareerHub.entity.JobOpening;
import com.careers.CareerHub.repository.JobOpeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobOpeningRepository jobRepo;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobOpening> addJob(@RequestBody JobOpening job){
        JobOpening saved = jobRepo.save(job);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<JobOpening>> listJobs(){
        return ResponseEntity.ok(jobRepo.findTop100ByOrderByPostedAtDesc());
    }
}
