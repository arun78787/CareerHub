package com.careers.CareerHub.repository;

import com.careers.CareerHub.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
