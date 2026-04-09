package com.soilpatrol.soilpatrolapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupabaseSensorReading {
    @JsonProperty("air_temp_c")
    public Double airTempC;

    @JsonProperty("air_humidity_pct")
    public Double airHumidityPct;

    @JsonProperty("soil_moisture_pct")
    public Double soilMoisturePct;

    @JsonProperty("soil_temp_c")
    public Double soilTempC;

    @JsonProperty("ph_level")
    public Double phLevel;

    @JsonProperty("nitrogen_mg")
    public Double nitrogenMg;

    @JsonProperty("phosphorus_mg")
    public Double phosphorusMg;

    @JsonProperty("potassium_mg")
    public Double potassiumMg;
}