package org.intecbrussel.service;

import org.intecbrussel.dto.AuthResponse;
import org.intecbrussel.dto.LoginRequest;
import org.intecbrussel.dto.RegisterRequest;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registreren
    public AuthResponse register(RegisterRequest request) {

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getFirstName() + " " + request.getLastName());
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setUserName(user.getFirstName() +  " " + user.getLastName());
//        response.setToken(null);
//        response.setExpiresAt(null);

        return response;
    }

    // Login
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));

        if(!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Ongeldig wachtwoord");
        }

        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setUserName(user.getFirstName() +  " " + user.getLastName());
        response.setRole(user.getRole().name());
//        response.setToken(null);
//        response.setExpiresAt(null);
        return response;


    }

}
