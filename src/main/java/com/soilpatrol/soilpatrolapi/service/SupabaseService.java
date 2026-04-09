package com.soilpatrol.soilpatrolapi.service;

import com.soilpatrol.soilpatrolapi.dto.SupabaseSensorReading;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SupabaseService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public SupabaseSensorReading getLatestReading(String deviceId) {
        String url = supabaseUrl + "/rest/v1/sensor_readings?device_id=eq." + deviceId + "&select=*&limit=1&order=recorded_at.desc";

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + supabaseKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SupabaseSensorReading[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                SupabaseSensorReading[].class
        );

        if (response.getBody() != null && response.getBody().length > 0) {
            return response.getBody()[0];
        } else {
            throw new RuntimeException("No live data found for device: " + deviceId);
        }
    }
}