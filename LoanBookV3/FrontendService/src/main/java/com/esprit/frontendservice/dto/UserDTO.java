package com.esprit.frontendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

@Getter
@Setter
public class UserDTO{
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private UserRole role;
    public UserDTO() {
        this.role = UserRole.SUBSCRIBER;
    }
}
