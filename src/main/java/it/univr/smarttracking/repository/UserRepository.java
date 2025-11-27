package it.univr.smarttracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univr.smarttracking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Login e ricerca utente tramite email
    Optional<User> findByEmail(String email);

    // Controllo duplicati
    boolean existsByEmail(String email);
}
