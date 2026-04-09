package com.soilpatrol.soilpatrolapi.service;

import com.soilpatrol.soilpatrolapi.dto.BrainDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OrchestratorService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${brain.url}")
    private String brainUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Part 1: Fetch from Supabase
    public BrainDTO.SupabaseReading fetchSensorData(String deviceId) {
        String url = supabaseUrl + "/rest/v1/sensor_readings?device_id=eq." + deviceId + "&select=*&limit=1&order=recorded_at.desc";

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + supabaseKey);

        ResponseEntity<BrainDTO.SupabaseReading[]> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), BrainDTO.SupabaseReading[].class
        );

        if (response.getBody() != null && response.getBody().length > 0) {
            return response.getBody()[0];
        }
        throw new RuntimeException("No live data found for device in Supabase.");
    }

    // Part 2: Send to Python Brain
    public Map<String, Object> askBrain(String endpoint, BrainDTO.BrainRequest requestBody) {
        String url = brainUrl + "/api/ai/" + endpoint;

        ResponseEntity<Map> response = restTemplate.postForEntity(
                url, requestBody, Map.class
        );

        return response.getBody();
    }
}