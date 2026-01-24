package com.careers.CareerHub.repository;

import com.careers.CareerHub.entity.Interview;
import com.careers.CareerHub.entity.InterviewStatus;
import com.careers.CareerHub.entity.User;
import com.careers.CareerHub.repository.projection.InterviewAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByUser(User user);

    long countByUserAndStatus(User user, InterviewStatus status);

    @Query("""
    SELECT 
        COUNT(CASE WHEN i.status = com.careers.CareerHub.entity.InterviewStatus.SCHEDULED THEN 1 END) AS scheduled,
        COUNT(CASE WHEN i.status = com.careers.CareerHub.entity.InterviewStatus.COMPLETED THEN 1 END) AS completed,
        COUNT(CASE WHEN i.status = com.careers.CareerHub.entity.InterviewStatus.CANCELLED THEN 1 END) AS cancelled
    FROM Interview i
    WHERE i.user = :user
    """)
    InterviewAnalytics interviewStats(@Param("user") User user);

}
