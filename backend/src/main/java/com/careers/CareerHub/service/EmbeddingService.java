package com.careers.CareerHub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.*;

@Service
public class EmbeddingService {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    @Value("${OPENAI_API_URL:https://api.openai.com/v1}")
    private String apiUrl;

    @Value("${OPENAI_EMBEDDING_MODEL:text-embedding-3-small}")
    private String embeddingModel;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final Jedis jedis = new Jedis(System.getenv().getOrDefault("REDIS_URL","localhost"), 6379);

    public float[] embedText(String idKey, String text) {
        try {
            Map<String,Object> body = Map.of("input", text, "model", embeddingModel);
            String reqJson = mapper.writeValueAsString(body);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/embeddings"))
                    .timeout(Duration.ofSeconds(20))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(reqJson))
                    .build();
            HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(resp.body());
            JsonNode vecNode = root.path("data").get(0).path("embedding");
            float[] vec = new float[vecNode.size()];
            for (int i = 0; i < vecNode.size(); i++) vec[i] = (float) vecNode.get(i).asDouble();

            // store in redis as comma-separated floats (small-scale)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < vec.length; i++) {
                if (i>0) sb.append(",");
                sb.append(vec[i]);
            }
            jedis.set("vec:" + idKey, sb.toString());
            return vec;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new float[0];
        }
    }

    public static double cosineSimilarity(float[] a, float[] b) {
        if (a.length != b.length) return -1;
        double dot=0, na=0, nb=0;
        for (int i=0;i<a.length;i++){
            dot += a[i]*b[i];
            na += a[i]*a[i];
            nb += b[i]*b[i];
        }
        return dot / (Math.sqrt(na) * Math.sqrt(nb) + 1e-10);
    }

    public Map<String,Double> searchSimilar(String idKeyPrefix, float[] queryVec, int topK) {
        Map<String,Double> scores = new TreeMap<>();
        Set<String> keys = jedis.keys(idKeyPrefix + "*");
        for (String k : keys) {
            String val = jedis.get(k);
            String[] parts = val.split(",");
            float[] v = new float[parts.length];
            for (int i=0;i<parts.length;i++) v[i] = Float.parseFloat(parts[i]);
            double sim = cosineSimilarity(queryVec, v);
            scores.put(k, sim);
        }
        // sort and return topK
        return scores; // caller can sort descending
    }
}
