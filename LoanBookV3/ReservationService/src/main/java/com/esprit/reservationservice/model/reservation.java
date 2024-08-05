package com.esprit.reservationservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
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


    @Column(name = "bookIDs", columnDefinition = "TEXT")
    private String bookIDs;

    @Transient
    private List<Integer> numbers;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void onLoad() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (bookIDs != null && !bookIDs.isEmpty()) {
            numbers = objectMapper.readValue(bookIDs, objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
        }
    }

    @PrePersist
    @PreUpdate
    private void onSave() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        bookIDs = objectMapper.writeValueAsString(numbers);
    }

    // Method to check if a number exists in the list
    public boolean containsNumber(Integer number) {
        return numbers != null && numbers.contains(number);
    }


}
