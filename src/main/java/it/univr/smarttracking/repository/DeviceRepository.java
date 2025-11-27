package it.univr.smarttracking.repository;

import it.univr.smarttracking.model.Device;
import it.univr.smarttracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByDeviceId(String deviceId);
<<<<<<< HEAD

    List<Device> findByOwner(User owner);
=======
    List<Device> findByOwnerId(Long ownerId);
    List<Device> findAll();
>>>>>>> 80c2f08e57b01cc7bac97725af1a2facc6283573
}
