package com.careers.CareerHub.controller;

import com.careers.CareerHub.dto.PublicPortfolioDto;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.ProjectRepository;
import com.careers.CareerHub.repository.ResumeRepository;
import com.careers.CareerHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ResumeRepository resumeRepository;

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> publicProfile(@PathVariable String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("id").descending() // safest default
        );

        return ResponseEntity.ok(
                new PublicPortfolioDto(
                        user.getFirstName() + " " + user.getLastName(),
                        user.getEmail(),
                        projectRepository.findByOwner(user, pageable).getContent(),
                        resumeRepository.findByUser(user).orElse(null)
                )
        );
    }
}
