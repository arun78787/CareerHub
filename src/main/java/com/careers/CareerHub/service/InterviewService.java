package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.Interview;
import com.careers.CareerHub.entity.InterviewStatus;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.InterviewRepository;
import com.careers.CareerHub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public Interview scheduleInterview(
            Instant scheduledAt,
            String interviewerName,
            String email
    ){
        User user = userRepository.findByEmail(email).orElseThrow();

        Interview interview = new Interview();
        interview.setScheduledAt(scheduledAt);
        interview.setInterviewerName(interviewerName);
        interview.setStatus(InterviewStatus.SCHEDULED);
        interview.setMeetingLink("http://meet.careerhub.dev/" + UUID.randomUUID());
        interview.setUser(user);

        return interviewRepository.save(interview);
    }
    public List<Interview> myInterviews(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        return interviewRepository.findByUser(user);
    }
}
