package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.*;
import com.careers.CareerHub.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final ProjectRepository projectRepository;
    private final PhotoRepository photoRepository;
    private final MockInterviewRepository mockInterviewRepository;
    private final InterviewRepository interviewRepository;

    private final ProjectService projectService;
    private final InterviewService interviewService;
    private final AuthService authService;

    @Override
    public User register(RegistrationRequest request){

    }


}
