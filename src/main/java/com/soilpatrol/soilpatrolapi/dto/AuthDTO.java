package com.soilpatrol.soilpatrolapi.dto;

public class AuthDTO {

    // What Android sends us
    public static class LoginRequest {
        public String email;
        public String password;
    }
    public static class RegisterRequest {
        public String fullName;
        public String phoneNumber;
        public String email;
        public String password;
    }
    // What we send back to Android
    public static class AuthResponse {
        public String ownerId;
        public String message;

        public AuthResponse(String ownerId, String message) {
            this.ownerId = ownerId;
            this.message = message;
        }
    }

    public static class DeviceRequest {
        public String deviceId;    // The hardware serial number
        public String ownerId;     // The farmer's UUID
        public String locationName; // e.g., "North Tomato Field"
        public String cropType;     // e.g., "Tomatoes"
    }
}