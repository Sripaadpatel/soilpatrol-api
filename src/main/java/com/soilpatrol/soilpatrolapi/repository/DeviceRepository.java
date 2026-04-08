package com.soilpatrol.soilpatrolapi.repository;

import com.soilpatrol.soilpatrolapi.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    // Custom query to fetch all devices owned by a specific farmer
    List<Device> findByOwnerId(UUID ownerId);
}