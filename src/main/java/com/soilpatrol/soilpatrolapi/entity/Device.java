package com.soilpatrol.soilpatrolapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "crop_type")
    private String cropType;

    @Column(name = "firmware_version")
    private String firmwareVersion = "1.0.0";

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "last_seen")
    private ZonedDateTime lastSeen;
}