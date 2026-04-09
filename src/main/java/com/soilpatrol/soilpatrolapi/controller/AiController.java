package com.soilpatrol.soilpatrolapi.controller;

import com.soilpatrol.soilpatrolapi.dto.AiRequestDTO;
import com.soilpatrol.soilpatrolapi.dto.SupabaseSensorReading;
import com.soilpatrol.soilpatrolapi.service.SupabaseService;
// Import your Gemini/AI Service here if you have one!
// import com.soilpatrol.soilpatrolapi.service.GeminiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private SupabaseService supabaseService;

    // @Autowired
    // private GeminiService geminiService;

    @PostMapping("/suggest")
    public ResponseEntity<?> getCropSuggestion(@RequestBody AiRequestDTO.SuggestionRequest request) {
        try {
            // 1. Fetch live data from Supabase securely
            SupabaseSensorReading liveData = supabaseService.getLatestReading(request.deviceId);

            // 2. Generate the report (Replace this with your actual Gemini logic)
            // String report = geminiService.generateSuggestion(liveData);
            String report = "Mock AI Report: Soil Nitrogen is " + liveData.nitrogenMg + " mg/kg. Soil is healthy!";

            // 3. Return to Android
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "report", report
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // You can add the @PostMapping("/check-in") and ("/suitability") here following the exact same pattern!
}