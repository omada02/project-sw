package it.univr.smarttracking.controller;

import it.univr.smarttracking.model.User;
import it.univr.smarttracking.service.DeviceService;
import it.univr.smarttracking.service.UserService;
import it.univr.smarttracking.util.QRCodeGenerator;

import com.google.zxing.WriterException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final UserService userService;

    public DeviceController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    // Mostra lista device utente
    @GetMapping
    public String listDevices(Model model, Authentication auth) {
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("devices", deviceService.getDevicesByUser(user));
        return "device-list";
    }

    // Pagina di provisioning
    @GetMapping("/provision")
    public String showProvisionPage() {
        return "device-provision";
    }

    // Crea device + qr code
    @PostMapping("/create")
    public String createDevice(Model model, Authentication auth) throws Exception {

        User user = userService.findByEmail(auth.getName());
        var device = deviceService.createDevice(user);

        // Genera QR
        var qr = QRCodeGenerator.generateQRCodeImage(device.getQrCodeData());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(qr, "PNG", out);
        String qrBase64 = Base64.getEncoder().encodeToString(out.toByteArray());

        model.addAttribute("qrCode", qrBase64);
        model.addAttribute("device", device);

        return "device-provision";
    }
}
