package com.careers.CareerHub.controller;

import com.careers.CareerHub.dto.UserResponseDto;
import com.careers.CareerHub.entity.User;
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

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody RegistrationRequest req){
        User saved = userService.register(req);
        UserResponseDto resp = mapToResponse(saved);
        return ResponseEntity.ok(resp);
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
