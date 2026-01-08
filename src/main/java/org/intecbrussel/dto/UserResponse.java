package org.intecbrussel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private long id;
    private String email;
    private String username;
    private String role;
}
