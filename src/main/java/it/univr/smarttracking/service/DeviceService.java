package it.univr.smarttracking.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import it.univr.smarttracking.model.Device;
import it.univr.smarttracking.model.User;
import it.univr.smarttracking.repository.DeviceRepository;

@Service
public class DeviceService {
    
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device provisionNewDevice(User owner) {
        Device device = new Device();
        device.setDeviceId(UUID.randomUUID().toString());
        device.setDeviceSecret(UUID.randomUUID().toString());
        device.setOwner(owner);
        device.setStatus(Device.DeviceStatus.PROVISIONED);

        // Genera QR Code
        String qrData = "SMARTTRACKING:" + device.getDeviceId() + ":" + device.getDeviceSecret();
        String qrCodeBase64 = generateQRCode(qrData);
        device.setQrCodeData(qrCodeBase64);

        return deviceRepository.save(device);
    }

    private String generateQRCode(String text) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Errore nella generazione del QR Code", e);
        }
    }

    public List<Device> getDevicesByUser(User user) {
        return deviceRepository.findByOwnerId(user.getId());
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}