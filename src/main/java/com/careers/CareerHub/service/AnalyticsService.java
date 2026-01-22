package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.InterviewRepository;
import com.careers.CareerHub.repository.UserRepository;
import com.careers.CareerHub.repository.projection.InterviewAnalytics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    public InterviewAnalytics interviewAnalytics(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        return interviewRepository.interviewStats(user);
    }
}
