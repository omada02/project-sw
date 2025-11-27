package it.univr.smarttracking.service;

import it.univr.smarttracking.model.Device;
import it.univr.smarttracking.model.Device.DeviceStatus;
import it.univr.smarttracking.model.User;
import it.univr.smarttracking.repository.DeviceRepository;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    // Genera un secret sicuro
    private String generateSecret() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    // Genera un deviceId univoco
    private String generateDeviceId() {
        return "DEV-" + System.currentTimeMillis();
    }

    // Crea un nuovo device e assegna un QR code
    public Device createDevice(User owner) {
        Device device = new Device();

        device.setDeviceId(generateDeviceId());
        device.setDeviceSecret(generateSecret());
        device.setOwner(owner);
        device.setStatus(DeviceStatus.INACTIVE);
        device.setCreatedAt(LocalDateTime.now());

        // Dati QR: es. "DEVICEID|SECRET"
        String qrData = device.getDeviceId() + "|" + device.getDeviceSecret();
        device.setQrCodeData(qrData);

        return deviceRepository.save(device);
    }

    // Lista device per utente
    public List<Device> getDevicesByUser(User owner) {
        return deviceRepository.findByOwner(owner);
    }

    // Lista device per admin
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    // Provisioning - attiva il device
    public Device provisionDevice(String deviceId, User owner) {
        Device device = deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("Device non trovato"));

        device.setOwner(owner);
        device.setStatus(DeviceStatus.PROVISIONED);
        device.setLastProvisionedAt(LocalDateTime.now());

        return deviceRepository.save(device);
    }
}
