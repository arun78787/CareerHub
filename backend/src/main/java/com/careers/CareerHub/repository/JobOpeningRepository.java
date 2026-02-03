package com.careers.CareerHub.repository;

import com.careers.CareerHub.entity.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
    List<JobOpening> findTop100ByOrderByPostedAtDesc();
}
