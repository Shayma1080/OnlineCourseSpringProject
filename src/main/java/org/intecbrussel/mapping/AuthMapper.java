package org.intecbrussel.mapping;

import org.intecbrussel.dto.AuthResponse;
import org.intecbrussel.model.User;

public class AuthMapper {

    public static AuthResponse toResponse(User user, String token) {
        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setToken(token);
        return response;
    }
}
