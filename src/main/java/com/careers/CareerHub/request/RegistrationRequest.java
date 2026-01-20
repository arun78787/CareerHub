package com.careers.CareerHub.request;

import com.careers.CareerHub.entity.Gender;
import com.careers.CareerHub.entity.Photo;
import com.careers.CareerHub.entity.Role;
import lombok.Data;

import java.time.Instant;

@Data
public class RegistrationRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String phoneNumber;
    private String email;
    private String password;
    private Role role;
    private Instant createdAt;

}
