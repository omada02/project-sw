package com.smarttracking.valid_soft.repository;

import com.smarttracking.valid_soft.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    // Rimuovi il metodo findAll() - è già ereditato da JpaRepository!
    // Spring fornisce automaticamente: save(), findAll(), findById(), delete(), etc.
}
