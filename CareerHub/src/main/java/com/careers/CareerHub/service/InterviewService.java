package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.Interview;
import com.careers.CareerHub.entity.InterviewStatus;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.InterviewRepository;
import com.careers.CareerHub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public Interview scheduleInterview(
            Instant scheduledAt,
            String interviewerName,
            String email
    ){
        User user = userRepository.findByEmail(email).orElseThrow();

        //create interview
        Interview interview = new Interview();
        interview.setScheduledAt(scheduledAt);
        interview.setInterviewerName(interviewerName);
        interview.setStatus(InterviewStatus.SCHEDULED);
        interview.setMeetingLink("http://meet.careerhub.dev/" + UUID.randomUUID());
        interview.setUser(user);

        //save interview
        Interview saved = interviewRepository.save(interview);

        //notification payload (simple Map to DTO)
        Map<String, Object> payload = Map.of(
                "type", "INTERVIEW_SCHEDULED",
                "id", saved.getId(),
                "scheduledAt",saved.getScheduledAt().toString(),
                "meetingLink", saved.getMeetingLink()
        );
        //send user-specific queue (destination : /user/{username}/queue/notifications)
        messagingTemplate.convertAndSendToUser(
                saved.getUser().getEmail(),
                "/queue/notifications",
                payload
        );

        return saved;
    }
    public List<Interview> myInterviews(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return interviewRepository.findByUser(user);
    }
}
