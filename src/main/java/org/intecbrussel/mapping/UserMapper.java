package org.intecbrussel.mapping;

import org.intecbrussel.dto.UserResponse;
import org.intecbrussel.model.User;

public class UserMapper {

    public static UserResponse toResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole( user.getRole().name());
        return response;
    }
}
