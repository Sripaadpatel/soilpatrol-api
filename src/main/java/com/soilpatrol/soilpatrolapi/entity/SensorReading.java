package com.soilpatrol.soilpatrolapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "sensor_readings")
@Data // Lombok: Auto-generates getters, setters, toString, equals
@NoArgsConstructor // Lombok: Auto-generates empty constructor for JPA
@AllArgsConstructor // Lombok: Auto-generates full constructor
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "recorded_at")
    private ZonedDateTime recordedAt;

    @Column(name = "air_temp_c")
    private BigDecimal airTempC;

    @Column(name = "air_humidity_pct")
    private BigDecimal airHumidityPct;

    @Column(name = "soil_moisture_pct")
    private BigDecimal soilMoisturePct;

    @Column(name = "soil_temp_c")
    private BigDecimal soilTempC;

    @Column(name = "ph_level")
    private BigDecimal phLevel;

    @Column(name = "nitrogen_mg")
    private BigDecimal nitrogenMg;

    @Column(name = "phosphorus_mg")
    private BigDecimal phosphorusMg;

    @Column(name = "potassium_mg")
    private BigDecimal potassiumMg;

    @Column(name = "battery_voltage")
    private BigDecimal batteryVoltage;
}