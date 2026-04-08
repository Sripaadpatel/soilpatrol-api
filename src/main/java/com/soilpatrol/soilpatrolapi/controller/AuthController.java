package com.soilpatrol.soilpatrolapi.controller;

import com.soilpatrol.soilpatrolapi.dto.AuthDTO;
import com.soilpatrol.soilpatrolapi.entity.Profile;
import com.soilpatrol.soilpatrolapi.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class AuthController {

    private final ProfileRepository repository;

    @Autowired
    public AuthController(ProfileRepository repository) {
        this.repository = repository;
    }

    // 1. The Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<AuthDTO.AuthResponse> register(@RequestBody AuthDTO.LoginRequest request) {
        if (repository.findByEmail(request.email).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthDTO.AuthResponse(null, "Email already exists"));
        }

        Profile newProfile = new Profile();
        newProfile.setId(UUID.randomUUID()); // Generate a new ID for the farmer
        newProfile.setEmail(request.email);
        newProfile.setPassword(request.password); // In a real prod app, we would hash this!
        newProfile.setCreatedAt(ZonedDateTime.now());

        repository.save(newProfile);
        return ResponseEntity.ok(new AuthDTO.AuthResponse(newProfile.getId().toString(), "Registration successful"));
    }

    // 2. The Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthDTO.AuthResponse> login(@RequestBody AuthDTO.LoginRequest request) {
        Optional<Profile> userOpt = repository.findByEmail(request.email);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.password)) {
            // Password matches! Send the UUID back to the Android app.
            return ResponseEntity.ok(new AuthDTO.AuthResponse(userOpt.get().getId().toString(), "Login successful"));
        } else {
            return ResponseEntity.status(401).body(new AuthDTO.AuthResponse(null, "Invalid email or password"));
        }
    }
}