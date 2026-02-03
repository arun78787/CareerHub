package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.NotificationQueue;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.NotificationQueueRepository;
import com.careers.CareerHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationSenderService {

    private final NotificationQueueRepository queueRepo;
    private final UserRepository userRepo;
    private final JavaMailSender mailSender;
    private final SimpMessagingTemplate messagingTemplate;

    // periodic flush; or call via @Scheduled
    @Transactional
    public void flushPending() {
        List<NotificationQueue> pending = queueRepo.findBySentFalse();
        for (NotificationQueue q : pending) {
            try {
                User u = userRepo.findById(q.getUserId()).orElse(null);
                if (u == null) {
                    q.setSent(true);
                    queueRepo.save(q);
                    continue;
                }
                // send email
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(u.getEmail());
                msg.setSubject("New job matches in your area");
                msg.setText("We found a job that matches your profile: jobId=" + q.getJobId() + "\nReason: " + q.getReason());
                mailSender.send(msg);

                // send websocket message (user-specific destination)
                messagingTemplate.convertAndSendToUser(u.getEmail(), "/queue/notifications",
                        Map.of("jobId", q.getJobId(), "reason", q.getReason()));

                q.setSent(true);
                queueRepo.save(q);
            } catch (Exception ex) {
                // log & continue; do not mark sent
                ex.printStackTrace();
            }
        }
    }
}
