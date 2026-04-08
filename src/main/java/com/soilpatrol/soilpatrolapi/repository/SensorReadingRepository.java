package com.soilpatrol.soilpatrolapi.repository;

import com.soilpatrol.soilpatrolapi.entity.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    // Spring Boot automatically writes the SQL query for this based on the method name!
    List<SensorReading> findByDeviceIdOrderByRecordedAtDesc(String deviceId);
}