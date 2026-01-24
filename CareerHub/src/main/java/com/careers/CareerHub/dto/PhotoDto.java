package com.careers.CareerHub.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PhotoDto {
    private Long id;
    private String fileName;
    private String fileType;
    private String url;
    private Long size;
    private Instant uploadedAt;
}
