package org.intecbrussel.dto;

import lombok.Getter;
import lombok.Setter;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private long id;
    private String email; // of user
    private String role; // STUDENT / INSTRUCTOR / ADMIN
    private LocalDateTime expiresAt;
}
