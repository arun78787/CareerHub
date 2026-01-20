package com.careers.CareerHub.dto;

import com.careers.CareerHub.entity.Gender;
import com.careers.CareerHub.entity.Role;
import lombok.Data;

import java.time.Instant;

@Data
public class UserSignupDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
