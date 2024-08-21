package com.esprit.authservice.service;

import com.esprit.authservice.model.User;
import com.esprit.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new User(user.getId(),user.getNom(),user.getPrenom(),user.getEmail(),user.getRole(),user.getPassword(),user.getReservations()))
                .collect(Collectors.toList());
    }
}
