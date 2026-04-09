package com.soilpatrol.soilpatrolapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrainDTO {

    // 1. What Supabase gives to Spring Boot
    public static class SupabaseReading {
        @JsonProperty("air_temp_c") public Double airTempC;
        @JsonProperty("air_humidity_pct") public Double airHumidityPct;
        @JsonProperty("soil_moisture_pct") public Double soilMoisturePct;
        @JsonProperty("soil_temp_c") public Double soilTempC;
        @JsonProperty("ph_level") public Double phLevel;
        @JsonProperty("nitrogen_mg") public Double nitrogenMg;
        @JsonProperty("phosphorus_mg") public Double phosphorusMg;
        @JsonProperty("potassium_mg") public Double potassiumMg;
    }

    // 2. The clean format Python expects
    public static class CleanSensorData {
        public Double airTempC;
        public Double airHumidityPct;
        public Double soilMoisturePct;
        public Double soilTempC;
        public Double phLevel;
        public Double nitrogenMg;
        public Double phosphorusMg;
        public Double potassiumMg;

        // Translator constructor
        public CleanSensorData(SupabaseReading raw) {
            this.airTempC = raw.airTempC;
            this.airHumidityPct = raw.airHumidityPct != null ? raw.airHumidityPct : 0.0;
            this.soilMoisturePct = raw.soilMoisturePct;
            this.soilTempC = raw.soilTempC != null ? raw.soilTempC : 0.0;
            this.phLevel = raw.phLevel;
            this.nitrogenMg = raw.nitrogenMg;
            this.phosphorusMg = raw.phosphorusMg;
            this.potassiumMg = raw.potassiumMg;
        }
    }

    // 3. What Spring Boot sends to Python
    public static class BrainRequest {
        public CleanSensorData sensorData;
        public String targetCrop; // Only used for check-in/suitability
        public String currentCrop; // Only used for check-in

        public BrainRequest(CleanSensorData sensorData) {
            this.sensorData = sensorData;
        }
    }
}