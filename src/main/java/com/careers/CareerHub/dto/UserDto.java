package com.careers.CareerHub.dto;

import com.careers.CareerHub.entity.Gender;
import com.careers.CareerHub.entity.Photo;
import com.careers.CareerHub.entity.Role;
import lombok.Data;

import java.time.Instant;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String phoneNumber;
    private String passwordHash;
    private String email;
    private Role role;
    private Instant createdAt;
    private Photo photo;
}
