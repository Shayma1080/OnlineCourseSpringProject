package org.intecbrussel.controller;

import org.intecbrussel.dto.UserResponse;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;
import org.intecbrussel.service.AuthService;
import org.intecbrussel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    @Autowired
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/role")
    public UserResponse updateRole(@PathVariable Long id, @RequestParam Role role) {
        return userService.changeRole(id,role);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
