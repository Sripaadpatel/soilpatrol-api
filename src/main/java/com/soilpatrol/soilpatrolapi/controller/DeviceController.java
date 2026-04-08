package com.soilpatrol.soilpatrolapi.controller;

import com.soilpatrol.soilpatrolapi.dto.AuthDTO;
import com.soilpatrol.soilpatrolapi.entity.Device;
import com.soilpatrol.soilpatrolapi.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
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
    public ResponseEntity<String> registerDevice(@RequestBody AuthDTO.DeviceRequest request) {
        Device device = new Device();
        device.setDeviceId(request.deviceId);
        device.setOwnerId(UUID.fromString(request.ownerId));
        device.setLocationName(request.locationName);
        device.setCropType(request.cropType);
        device.setLastSeen(ZonedDateTime.now());

        deviceRepository.save(device);
        return ResponseEntity.ok("Device registered successfully");
    }

    // 2. Get all devices for a specific farmer (Used for the Android App Dashboard)
    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<Device>> getUserDevices(@PathVariable UUID ownerId) {
        List<Device> devices = deviceRepository.findByOwnerId(ownerId);
        return ResponseEntity.ok(devices);
    }
}