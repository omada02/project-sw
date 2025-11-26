package it.univr.smarttracking.controller;

import it.univr.smarttracking.model.User;
import it.univr.smarttracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ======================
    // MOSTRA PAGINA LOGIN
    // ======================
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("title", "Login - SmartTracking");
        return "login";
    }

    // ======================
    // MOSTRA PAGINA REGISTRAZIONE
    // ======================
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("title", "Registrazione - SmartTracking");
        return "register";
    }

    // ======================
    // PROCESSO REGISTRAZIONE
    // ======================
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        try {
            System.out.println("📝 Tentativo registrazione: " + email);

            // Verifica se l'utente esiste già
            if (userRepository.existsByEmail(email)) {
                model.addAttribute("error", "Email già registrata!");
                return "register";
            }

            if (userRepository.existsByUsername(username)) {
                model.addAttribute("error", "Username già esistente!");
                return "register";
            }

            // Crea nuovo utente
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("USER");

            // Salva utente nel database
            userRepository.save(user);

            System.out.println("✅ Utente registrato: " + user.getEmail());

            // Reindirizza al login con messaggio di successo
            model.addAttribute("success", "Registrazione completata! Ora puoi fare il login.");
            return "login";

        } catch (Exception e) {
            System.out.println("❌ Errore registrazione: " + e.getMessage());
            model.addAttribute("error", "Errore durante la registrazione: " + e.getMessage());
            return "register";
        }
    }
}