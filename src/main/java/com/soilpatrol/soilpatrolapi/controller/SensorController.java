package com.soilpatrol.soilpatrolapi.controller;

import com.soilpatrol.soilpatrolapi.entity.SensorReading;
import com.soilpatrol.soilpatrolapi.repository.DeviceRepository;
import com.soilpatrol.soilpatrolapi.repository.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorReadingRepository repository;
    private final DeviceRepository deviceRepository; // 1. Added this line

    @Autowired
    public SensorController(SensorReadingRepository repository, DeviceRepository deviceRepository) {
        this.repository = repository;
        this.deviceRepository = deviceRepository; // 2. Added to constructor
    }

    // Endpoint 1: For the Raspberry Pi to push data
    @PostMapping("/data")
    public ResponseEntity<String> receiveSensorData(@RequestBody SensorReading reading) {
        try {
            // Ensure the timestamp is set if the Pi didn't provide one
            if (reading.getRecordedAt() == null) {
                reading.setRecordedAt(ZonedDateTime.now());
            }

            // Update the device's last_seen timestamp
            deviceRepository.findById(reading.getDeviceId()).ifPresent(device -> {
                device.setLastSeen(ZonedDateTime.now());
                deviceRepository.save(device);
            });

            // Save the sensor data to Supabase
            repository.save(reading);

            return ResponseEntity.ok("Data successfully saved for device: " + reading.getDeviceId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error saving data: " + e.getMessage());
        }
    }

    // Endpoint 2: For your future Android App to fetch the latest data
    @GetMapping("/data/{deviceId}")
    public ResponseEntity<List<SensorReading>> getLatestData(@PathVariable String deviceId) {
        // Fetch the 10 most recent readings for this specific device
        List<SensorReading> latestReadings = repository.findByDeviceIdOrderByRecordedAtDesc(deviceId);
        return ResponseEntity.ok(latestReadings);
    }
}