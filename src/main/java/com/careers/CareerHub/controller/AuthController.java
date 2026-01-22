package com.careers.CareerHub.controller;

import com.careers.CareerHub.dto.UserLoginDto;
import com.careers.CareerHub.dto.UserResponseDto;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.service.AuthService;
import com.careers.CareerHub.service.UserService;
import com.careers.CareerHub.dto.auth.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegistrationRequest request){
        User saved = userService.register(request);
        return ResponseEntity.ok(mapToResponse(saved));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginDto request){
        return ResponseEntity.ok(authService.login(request));
    }
    private UserResponseDto mapToResponse(User u){
        UserResponseDto dto = new UserResponseDto();
        dto.setId(u.getId());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole());
        return dto;
    }
}
