package com.esprit.reservationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class reservation {
    @GeneratedValue
    @Id
    private Integer id;

    @Column(nullable = false)
    private LocalDate date_intake;

    @Column(nullable = false)
    private LocalDate date_take_back;

    @ElementCollection
    @CollectionTable(name = "reservation_books", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "book_id")
    private List<Integer> bookIds;
}
