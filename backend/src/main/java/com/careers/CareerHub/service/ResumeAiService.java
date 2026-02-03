package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.Resume;
import com.careers.CareerHub.repository.ResumeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResumeAiService {

    private final ResumeRepository resumeRepository;
    private final S3Service s3Service;
    private final AiClient aiClient;
    private final ObjectMapper objectMapper;
    private final Tika tika = new Tika();

    @Async
    public void reviewResumeAsync(Long resumeId){
        try{
            Resume resume = resumeRepository.findById(resumeId).orElseThrow();
            if(resume.getFileUrl() == null) return;

            //download PDF bytes from S3
            byte[] bytes = s3Service.downloadFileBytes(resume.getFileUrl());

            //extract text using Tika
            String text;
            try(InputStream inputStream = new ByteArrayInputStream(bytes)){
                text = tika.parseToString(inputStream);

            }

            //optionally extract simple parsed skills using regex / heuristics(for MVP skip)
            //call AI client for review(mock or real)
            Map<String, Object> review = aiClient.reviewResume(text);

            //persist results
            resume.setAiReview(objectMapper.writeValueAsString(review));
            Object s = review.get("score");
            if(s instanceof Number){
                resume.setAiScore(((Number) s).intValue());
            }
            resumeRepository.save(resume);
        } catch(Exception ex){
            //log error (avoid exposing PII)
            System.out.println("AI review failed for resume " + resumeId + " : " + ex.getMessage());
        }
    }
}
