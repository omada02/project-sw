package it.univr.smarttracking.model;

import java.time.LocalDateTime; // Spring Boot 3 usa jakarta invece di javax

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String deviceId;

    private String deviceSecret;
    private String qrCodeData;
    
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private LocalDateTime createdAt;
    private LocalDateTime lastProvisionedAt;

    // Costruttori
    public Device() {
        this.createdAt = LocalDateTime.now();
        this.status = DeviceStatus.INACTIVE;
    }

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getDeviceSecret() { return deviceSecret; }
    public void setDeviceSecret(String deviceSecret) { this.deviceSecret = deviceSecret; }

    public String getQrCodeData() { return qrCodeData; }
    public void setQrCodeData(String qrCodeData) { this.qrCodeData = qrCodeData; }

    public DeviceStatus getStatus() { return status; }
    public void setStatus(DeviceStatus status) { this.status = status; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastProvisionedAt() { return lastProvisionedAt; }
    public void setLastProvisionedAt(LocalDateTime lastProvisionedAt) { this.lastProvisionedAt = lastProvisionedAt; }

    public enum DeviceStatus {
        ACTIVE, INACTIVE, PROVISIONED
    }
}