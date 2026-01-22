package com.careers.CareerHub.repository;

import com.careers.CareerHub.entity.Project;
import com.careers.CareerHub.entity.Resume;
import com.careers.CareerHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Resume> findByUser(User user);

}
