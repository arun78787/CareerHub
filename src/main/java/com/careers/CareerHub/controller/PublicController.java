package com.careers.CareerHub.controller;

import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.ProjectRepository;
import com.careers.CareerHub.repository.ResumeRepository;
import com.careers.CareerHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ResumeRepository resumeRepository;

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> publicProfile(@PathVariable String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return ResponseEntity.ok(
                new PublicProfileDto(
                        user.getFirstName() + " " + user.getLastName(),
                        user.getEmail(),
                        projectRepository.findByUser(user),
                        resumeRepository.findByUser(user).orElse(null)
                )
        );
    }
}
