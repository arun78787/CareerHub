package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.JobOpening;
import com.careers.CareerHub.entity.NotificationQueue;
import com.careers.CareerHub.entity.Resume;
import com.careers.CareerHub.repository.JobOpeningRepository;
import com.careers.CareerHub.repository.NotificationQueueRepository;
import com.careers.CareerHub.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobMatcherService {

    private final JobOpeningRepository jobRepo;
    private final ResumeRepository resumeRepo;
    private final NotificationQueueRepository noteRepo;

    // naive scheduled matcher runs every 1 minute (tweak in prod)
    @Scheduled(fixedDelayString = "${jobs.matcher.interval.ms:60000}")
    public void runMatcher() {
        List<JobOpening> jobs = jobRepo.findTop100ByOrderByPostedAtDesc();
        List<Resume> resumes = resumeRepo.findAll();

        for (JobOpening job : jobs) {
            Set<String> jobSkills = parseJsonArray(job.getSkills());

            for (Resume r : resumes) {
                if (r.getParsedSkills() == null) continue;
                Set<String> resumeSkills = parseJsonArray(r.getParsedSkills());

                // naive intersection & threshold
                long common = resumeSkills.stream().filter(s -> jobSkills.contains(s)).count();
                int threshold = Math.max(1, Math.min(3, jobSkills.size() / 3));
                if (common >= threshold) {
                    NotificationQueue q = new NotificationQueue();
                    q.setJobId(job.getId());
                    q.setUserId(r.getUser().getId());
                    q.setReason("matched_skills:" + common);
                    noteRepo.save(q);
                }
            }
        }
    }

    private Set<String> parseJsonArray(String json) {
        if (json == null) return Set.of();
        try {
            // crude parse: remove brackets and quotes
            String cleaned = json.replaceAll("[\\[\\]\"]", "");
            return Arrays.stream(cleaned.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            return Set.of();
        }
    }
}
