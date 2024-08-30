package com.esprit.authservice.service;

import com.esprit.authservice.model.Book;
import com.esprit.authservice.model.User;
import com.esprit.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public List<Map<String,Object>> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user->{
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id",user.getId());
                    userMap.put("nom",user.getNom());
                    userMap.put("prenom",user.getPrenom());
                    userMap.put("email",user.getEmail());
                    userMap.put("role",user.getRole());
                    userMap.put("reservation",user.getReservations().stream().map(reservation -> {
                                Book book = reservation.getBook();
                                return book.getTitle() + " by " + book.getAuthor();
                            })
                            .collect(Collectors.toList()));
                    return userMap;
                })
//                .map(user -> new User(user.getId(),user.getNom(),user.getPrenom(),user.getEmail(),user.getRole(),user.getReservations()))
                .collect(Collectors.toList());
    }
    //TO DO 1 -->Done
    // add the list of reservations that the user has each reservation in the prod here will be seen only by the title of the book and the author

    //TO DO 2
    //The admin confirms the returning of the books(incrementing the number of books again)

    //TO DO 3(optional)
    //if we are past the date limit concerning the returning of the books an email will be sent(optional)
}
