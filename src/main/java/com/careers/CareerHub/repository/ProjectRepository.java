package com.careers.CareerHub.repository;

import com.careers.CareerHub.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
