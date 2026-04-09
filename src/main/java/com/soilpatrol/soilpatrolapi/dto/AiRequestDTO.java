package com.soilpatrol.soilpatrolapi.dto;

public class AiRequestDTO {

    public static class SuggestionRequest {
        public String deviceId;
    }

    public static class CheckInRequest {
        public String deviceId;
        public String currentCrop;
    }

    public static class SuitabilityRequest {
        public String deviceId;
        public String targetCrop;
    }
}