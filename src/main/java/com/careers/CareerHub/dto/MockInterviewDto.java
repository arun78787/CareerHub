package com.careers.CareerHub.dto;

import com.careers.CareerHub.entity.Interview;
import lombok.Data;

@Data
public class MockInterviewDto {
    private Long id;
    private String videoUrl;
    private Integer score;
    private Interview interview;
    private String feedback;
}
