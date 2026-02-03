package com.careers.CareerHub.service;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MockAiClient implements AiClient{

    @Override
    public Map<String, Object> reviewResume(String resumeText){
        //super-simple heuristic mock: score by length of text (dev only)
        int score = Math.min(90, Math.max(20, resumeText.length() /50));
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("score", score);
        out.put("strengths", List.of("Clear backend experience", "AWS usage mentioned"));
        out.put("weaknesses", List.of("No quantified metrics", "Add unit tests summary"));
        out.put("recommended_roles", List.of("Backend Engineer", "Platform SRE"));
        out.put("skills_matches", Map.of("Spring Boot", Math.min(10, resumeText.contains("Spring") ? 8 : 4)));
        out.put("improvement_actions", List.of("Add bullet points with metrics", "List test coverage"));
        return out;
    }
}
