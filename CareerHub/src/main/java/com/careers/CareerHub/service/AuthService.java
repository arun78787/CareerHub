package com.careers.CareerHub.service;

import com.careers.CareerHub.dto.UserLoginDto;
import com.careers.CareerHub.dto.UserResponseDto;
import com.careers.CareerHub.dto.auth.AuthResponse;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(UserLoginDto request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPasswordHash())){
            throw new RuntimeException("Invalid Credentials");
        }
        String token = jwtService.generateToken(user);

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return new AuthResponse(token, dto);
    }
}
