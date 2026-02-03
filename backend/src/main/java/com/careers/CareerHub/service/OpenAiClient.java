package com.careers.CareerHub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.*;

@Component
public class OpenAiClient implements AiClient {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    @Value("${OPENAI_API_URL:https://api.openai.com/v1}")
    private String apiUrl;

    @Value("${OPENAI_MODEL:gpt-4o-mini}")
    private String model;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Override
    public Map<String, Object> reviewResume(String resumeText) {
        try {
            // Keep prompt compact — ask LLM to return valid JSON only
            String system = "You are a senior technical recruiter. Given resume text, return a single valid JSON object only.";
            String user = """
                Return JSON:
                {
                  "score": 0-100,
                  "strengths": ["..."],
                  "weaknesses": ["..."],
                  "recommended_roles": ["..."],
                  "skill_matches": {"SkillName": 1-10},
                  "improvement_actions": ["..."]
                }
                
                ResumeText:
                """ + resumeText;

            // build body for Chat Completions (OpenAI Chat API)
            Map<String,Object> body = new HashMap<>();
            body.put("model", model);
            List<Map<String,String>> messages = List.of(
                    Map.of("role", "system", "content", system),
                    Map.of("role", "user", "content", user)
            );
            body.put("messages", messages);
            body.put("temperature", 0.0);
            body.put("max_tokens", 800);

            String requestBody = mapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/chat/completions"))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // simple retry loop
            int tries = 0;
            while (tries < 4) {
                tries++;
                HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                int status = resp.statusCode();
                if (status >= 200 && status < 300) {
                    JsonNode root = mapper.readTree(resp.body());
                    // extract top assistant message
                    JsonNode first = root.path("choices").get(0).path("message").path("content");
                    String content = first.asText();
                    // The assistant should produce raw JSON. Try parse.
                    try {
                        Map<String,Object> map = mapper.readValue(content, Map.class);
                        return map;
                    } catch (Exception parseEx) {
                        // fallback: try to extract JSON substring.
                        int start = content.indexOf("{");
                        int end = content.lastIndexOf("}");
                        if (start >=0 && end > start) {
                            String json = content.substring(start, end+1);
                            Map<String,Object> map = mapper.readValue(json, Map.class);
                            return map;
                        } else {
                            // content not JSON - wrap fallback
                            Map<String,Object> fallback = new LinkedHashMap<>();
                            fallback.put("score", 50);
                            fallback.put("strengths", List.of("AI returned unparsable output"));
                            fallback.put("weaknesses", List.of("Model output not parseable"));
                            fallback.put("recommended_roles", List.of("Backend Engineer"));
                            fallback.put("skill_matches", Map.of());
                            fallback.put("improvement_actions", List.of("Retry with different prompt"));
                            return fallback;
                        }
                    }
                } else if (status == 429 || status >= 500) {
                    // retryable
                    Thread.sleep(1000L * tries * 2);
                    continue;
                } else {
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // ultimate fallback
        Map<String,Object> fallback = new LinkedHashMap<>();
        fallback.put("score", 40);
        fallback.put("strengths", List.of());
        fallback.put("weaknesses", List.of("AI unavailable"));
        fallback.put("recommended_roles", List.of());
        fallback.put("skill_matches", Map.of());
        fallback.put("improvement_actions", List.of());
        return fallback;
    }
}
