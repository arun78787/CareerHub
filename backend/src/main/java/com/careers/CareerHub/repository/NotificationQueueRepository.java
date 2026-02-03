package com.careers.CareerHub.repository;

import com.careers.CareerHub.entity.NotificationQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationQueueRepository extends JpaRepository<NotificationQueue, Long> {
    List<NotificationQueue> findBySentFalse();
}
