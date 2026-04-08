package com.soilpatrol.soilpatrolapi.controller;

import com.soilpatrol.soilpatrolapi.entity.Device;
import com.soilpatrol.soilpatrolapi.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    // 1. Register a new device to a user
    @PostMapping("/register")
    public ResponseEntity<String> registerDevice(@RequestBody Device device) {
        try {
            // Check if device ID is already taken
            if (deviceRepository.existsById(device.getDeviceId())) {
                return ResponseEntity.badRequest().body("Device ID already registered.");
            }
            deviceRepository.save(device);
            return ResponseEntity.ok("Device " + device.getDeviceId() + " successfully registered.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error registering device: " + e.getMessage());
        }
    }

    // 2. Get all devices for a specific farmer (Used for the Android App Dashboard)
    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<Device>> getUserDevices(@PathVariable UUID ownerId) {
        List<Device> devices = deviceRepository.findByOwnerId(ownerId);
        return ResponseEntity.ok(devices);
    }
}