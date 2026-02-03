package com.careers.CareerHub.service;

import java.util.Map;

public interface AiClient {
    // given resume plain text, return a structured review map.
    //example keys : "score" -> Integer,"strengths" -> List<String>,"weaknesses" -> List<String> , etc.
    Map<String, Object> reviewResume(String resumeText);
}
