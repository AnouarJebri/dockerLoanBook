package com.esprit.authservice.controller;

import com.esprit.authservice.model.User;
import com.esprit.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
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

        // Create a session and set user details
//        HttpSession session = request.getSession();
//        session.setAttribute("user", user);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful.");
        response.put("role", user.getRole().name()); // Assuming role is an enum
        response.put("name", user.getNom()+" "+user.getPrenom());
        response.put("id",user.getId());

        return ResponseEntity.ok(response);
    }



    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }


//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
//        // Invalidate the session on the server side
////        HttpSession session = request.getSession(false); // false to avoid creating a new session if one does not exist
////        if (session != null) {
////            session.invalidate(); // Invalidate the session
////        }
//
//        // Remove the session cookie from the client side
//       /* Cookie cookie = new Cookie("JSESSIONID", null);
//        cookie.setPath("/"); // Set the path to ensure the cookie is removed
//        cookie.setMaxAge(0); // Set max age to 0 to remove the cookie immediately
//        response.addCookie(cookie);*/
//
//        // Prepare the response
//        Map<String, String> responseBody = new HashMap<>();
//        responseBody.put("message", "Logout successful.");
//
//        return ResponseEntity.ok(responseBody);
//    }



//    @GetMapping("/checkSession")
//    public ResponseEntity<Map<String, Boolean>> checkSession(HttpServletRequest request) {
//        HttpSession session = request.getSession(false); // Get the current session, if it exists
//        boolean isValid = (session != null); // Check if the session is not null
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("valid", isValid);
//        return ResponseEntity.ok(response); // Return the session validity as JSON
//    }

    /*@CrossOrigin(origins = "http://localhost:8085", allowCredentials = "true")
    @GetMapping("/restrictedPage")
    public ResponseEntity<?> restrictedPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return ResponseEntity.ok("Welcome to the restricted page");
    }*/
}
//private RestTemplate restTemplate;
    /*@GetMapping("/myconfig")
    public String get(){
        String url = "http://configserver:8888/authservice/default";
        return restTemplate.getForObject(url, String.class);
    }*/