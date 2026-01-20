package com.careers.CareerHub.repository;

import com.careers.CareerHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
