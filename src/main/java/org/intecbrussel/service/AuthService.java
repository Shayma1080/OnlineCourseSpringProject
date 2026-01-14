package org.intecbrussel.service;

import lombok.RequiredArgsConstructor;
import org.intecbrussel.dto.AuthResponse;
import org.intecbrussel.dto.LoginRequest;
import org.intecbrussel.dto.RegisterRequest;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.UserRepository;
import org.intecbrussel.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor()
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    // Registreren
    public AuthResponse register(RegisterRequest request) {


        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
                .orElseThrow(() -> new UsernameNotFoundException("Gebruiker niet gevonden"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Ongeldig wachtwoord");
        }

        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setUserName(user.getFirstName() +  " " + user.getLastName());
        response.setRole(user.getRole().name());

        String token = jwtService.generateToken(user);
        response.setToken(token);
//        response.setExpiresAt(null);
        return response;


    }

}
