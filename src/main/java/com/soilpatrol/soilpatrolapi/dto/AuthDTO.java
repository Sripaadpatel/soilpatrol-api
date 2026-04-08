package com.soilpatrol.soilpatrolapi.dto;

public class AuthDTO {

    // What Android sends us
    public static class LoginRequest {
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
}