package it.univr.smarttracking.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.univr.smarttracking.model.Device;
import it.univr.smarttracking.model.User;
import it.univr.smarttracking.service.DeviceService;
import it.univr.smarttracking.service.UserService;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final UserService userService;

    public DeviceController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @GetMapping("/provision")
    public String showProvisionPage(Model model) {
        model.addAttribute("title", "Provisioning Device - SmartTracking");
        return "device-provision";
    }

    @PostMapping("/provision")
    public String provisionDevice(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            String username = userDetails.getUsername();
            User user = userService.findByUsername(username);
            Device device = deviceService.provisionNewDevice(user);
            
            model.addAttribute("device", device);
            model.addAttribute("message", "Dispositivo provisionato con successo!");
            return "device-provision";
        } catch (Exception e) {
            model.addAttribute("error", "Errore durante il provisioning: " + e.getMessage());
            return "device-provision";
        }
    }

    @GetMapping("/list")
    public String listUserDevices(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        List<Device> devices = deviceService.getDevicesByUser(user);
        
        model.addAttribute("devices", devices);
        model.addAttribute("title", "I tuoi Dispositivi - SmartTracking");
        return "device-list";
    }
}