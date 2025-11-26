package it.univr.smarttracking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univr.smarttracking.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByDeviceId(String deviceId);
    List<Device> findByOwnerId(Long ownerId);
    List<Device> findAll();
}
