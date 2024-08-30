package com.esprit.authservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User {
    @GeneratedValue
    @Id
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Transient
    private List<String> bookTitlesAndAuthors;

    public User() {
        this.role = UserRole.SUBSCRIBER;
    }

    public User(Integer id, String nom, String prenom, String email, UserRole role, List<Reservation> reservations) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.reservations = reservations;
        this.bookTitlesAndAuthors = this.reservations.stream()
                .map(reservation -> {
                    Book book = reservation.getBook();
                    return book.getTitle() + " by " + book.getAuthor();
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", reservations=" + reservations+
                ", bookTitlesAndAuthors=" + bookTitlesAndAuthors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom) && Objects.equals(email, user.email) && role == user.role && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, email, role, password);
    }
}
