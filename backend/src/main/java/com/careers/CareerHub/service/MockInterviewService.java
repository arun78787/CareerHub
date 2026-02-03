package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.MockInterview;
import com.careers.CareerHub.entity.InterviewRound;
import com.careers.CareerHub.repository.MockInterviewRepository;
import com.careers.CareerHub.repository.InterviewRoundRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MockInterviewService {

    private final MockInterviewRepository mockRepo;
    private final InterviewRoundRepository roundRepo;
    private final AiClient aiClient; // use same client to generate structured JSON
    private final ObjectMapper mapper = new ObjectMapper();

    public MockInterview generateForJob(Long userId, Long jobId, String resumeText, String roleName, String jobDescription) {
        // Build prompt
        String prompt = "Create 3 rounds for role: " + roleName + ". Candidate resume: " + resumeText +
                " Job description: " + jobDescription +
                " Return JSON: [{round_type:'', difficulty:'', questions:[{q:'', model_answer:'', score_guide:''}]}]";

        Map<String,Object> resp = aiClient.reviewResume(prompt); // reuse interface — but ideally add a dedicated method
        // Expected resp to contain structured rounds (you must ensure your AiClient can handle this prompt)
        try {
            MockInterview mi = new MockInterview();
            mi.setUserId(userId);
            mi.setJobId(jobId);
            mi.setStatus("CREATED");
            MockInterview saved = mockRepo.save(mi);

            // parse resp and persist rounds
            Object roundsObj = resp.get("rounds");
            if (roundsObj instanceof List) {
                List<Map<String,Object>> rounds = (List) roundsObj;
                int idx = 1;
                for (Map<String,Object> r : rounds) {
                    InterviewRound ir = new InterviewRound();
                    ir.setMockInterviewId(saved.getId());
                    ir.setRoundIndex(idx++);
                    ir.setRoundType((String) r.get("round_type"));
                    ir.setQuestions(mapper.writeValueAsString(r.get("questions")));
                    roundRepo.save(ir);
                }
            }
            return saved;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
