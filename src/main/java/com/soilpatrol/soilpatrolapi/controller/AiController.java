package com.soilpatrol.soilpatrolapi.controller;

import com.soilpatrol.soilpatrolapi.dto.BrainDTO;
import com.soilpatrol.soilpatrolapi.service.OrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private OrchestratorService orchestrator;

    // ==========================================
    // 1. SUGGEST CROP ENDPOINT
    // ==========================================
    @PostMapping("/suggest")
    public ResponseEntity<?> getSuggestion(@RequestBody Map<String, String> request) {
        try {
            String deviceId = request.get("deviceId");

            // Fetch, Clean, and Wrap
            BrainDTO.SupabaseReading rawData = orchestrator.fetchSensorData(deviceId);
            BrainDTO.CleanSensorData cleanData = new BrainDTO.CleanSensorData(rawData);
            BrainDTO.BrainRequest brainRequest = new BrainDTO.BrainRequest(cleanData);

            // Ask Python Brain
            Map<String, Object> finalReport = orchestrator.askBrain("suggest", brainRequest);

            return ResponseEntity.ok(finalReport);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // ==========================================
    // 2. CHECK-IN ENDPOINT (Needs currentCrop)
    // ==========================================
    @PostMapping("/check-in")
    public ResponseEntity<?> getCheckIn(@RequestBody Map<String, String> request) {
        try {
            String deviceId = request.get("deviceId");
            String currentCrop = request.get("currentCrop"); // Extracted from Android payload

            // Fetch, Clean, and Wrap
            BrainDTO.SupabaseReading rawData = orchestrator.fetchSensorData(deviceId);
            BrainDTO.CleanSensorData cleanData = new BrainDTO.CleanSensorData(rawData);
            BrainDTO.BrainRequest brainRequest = new BrainDTO.BrainRequest(cleanData);

            // Attach the specific parameter for this endpoint
            brainRequest.currentCrop = currentCrop;

            // Ask Python Brain
            Map<String, Object> finalReport = orchestrator.askBrain("check-in", brainRequest);

            return ResponseEntity.ok(finalReport);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // ==========================================
    // 3. SUITABILITY ENDPOINT (Needs targetCrop)
    // ==========================================
    @PostMapping("/suitability")
    public ResponseEntity<?> getSuitability(@RequestBody Map<String, String> request) {
        try {
            String deviceId = request.get("deviceId");
            String targetCrop = request.get("targetCrop"); // Extracted from Android payload

            // Fetch, Clean, and Wrap
            BrainDTO.SupabaseReading rawData = orchestrator.fetchSensorData(deviceId);
            BrainDTO.CleanSensorData cleanData = new BrainDTO.CleanSensorData(rawData);
            BrainDTO.BrainRequest brainRequest = new BrainDTO.BrainRequest(cleanData);

            // Attach the specific parameter for this endpoint
            brainRequest.targetCrop = targetCrop;

            // Ask Python Brain
            Map<String, Object> finalReport = orchestrator.askBrain("suitability", brainRequest);

            return ResponseEntity.ok(finalReport);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}