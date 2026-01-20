package com.careers.CareerHub.controller;

import com.careers.CareerHub.dto.UserResponseDto;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.careers.CareerHub.dto.auth.RegistrationRequest;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

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
