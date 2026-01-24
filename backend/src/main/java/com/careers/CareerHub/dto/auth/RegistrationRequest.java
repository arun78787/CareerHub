package com.careers.CareerHub.dto.auth;

import com.careers.CareerHub.entity.Gender;
import com.careers.CareerHub.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private Role role; // optional, default USER if null
}
