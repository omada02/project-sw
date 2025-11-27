package it.univr.smarttracking.repository;

import it.univr.smarttracking.model.Device;
import it.univr.smarttracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByDeviceId(String deviceId);

    List<Device> findByOwner(User owner);
}
