package com.careers.CareerHub.dto.auth;

import com.careers.CareerHub.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserResponseDto user;
}
