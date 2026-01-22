package com.careers.CareerHub.repository.projection;

public interface InterviewAnalytics {
    long getScheduled();
    long getCompleted();
    long getCancelled();
}
