package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.Role;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.*;
import com.careers.CareerHub.dto.auth.RegistrationRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final ResumeRepository resumeRepository;
//    private final ProjectRepository projectRepository;
//    private final PhotoRepository photoRepository;
//    private final MockInterviewRepository mockInterviewRepository;
//    private final InterviewRepository interviewRepository;
//
//    private final ProjectService projectService;
//    private final InterviewService interviewService;
//    private final AuthService authService;

   //to register a new user:
    @Transactional
    public User register(RegistrationRequest request){
        //basic validation email exists
        userRepository.findByEmail(request.getEmail()).ifPresent(u->{
            throw new IllegalArgumentException("Email already in use" + request.getEmail());
        });
        //map request -> entity
        User user = mapToUser(request);

        //hash password
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        //default role:
        if(user.getRole() == null){
            user.setRole(Role.USER);
        }
        //save and return
        return userRepository.save(user);
    }
    //simple mapper keeps mapping logic inside service for now
    private User mapToUser(RegistrationRequest req){
        User u = new User();
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setEmail(req.getEmail());
        u.setPhoneNumber(req.getPhoneNumber());
        u.setGender(req.getGender());
        u.setRole(req.getRole()); //may be null
        // don't set passwordHash here (we set it separately)
        // don't set createdAt — CreationTimestamp handles it
        return u;
    }


}
