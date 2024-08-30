package com.esprit.authservice.controller;

import com.esprit.authservice.model.User;
import com.esprit.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esprit.authservice.model.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        // Check if user with the same email already exists
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return "User with this email already exists.";
        }

        // Set default role to SUBSCRIBER
        user.setRole(UserRole.SUBSCRIBER);

        // Save the user
        userService.saveUser(user);
        return "User registered successfully";
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser/*, HttpServletRequest request*/) {
        // Check if the user exists
        User user = userService.findByEmail(loginUser.getEmail()).orElse(null);
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful.");
        response.put("role", user.getRole().name()); // Assuming role is an enum
        response.put("name", user.getNom()+" "+user.getPrenom());
        response.put("id",user.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<Map<String, Object>> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

}
