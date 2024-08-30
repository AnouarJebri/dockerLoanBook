package com.esprit.reservationservice.controller;

import com.esprit.reservationservice.DTO.ReservationRequestDTO;
import com.esprit.reservationservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/test")
    public String test(){
        return "well maven is now dockerized !";
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveCart(@RequestBody ReservationRequestDTO requestDTO){
        Set<Integer> uniqueBookIds = new HashSet<>(requestDTO.getBookIds());
        if (uniqueBookIds.size() < requestDTO.getBookIds().size()) {
            return ResponseEntity.badRequest().body("Duplicate book reservations are not allowed.");
        }
        try {
            reservationService.createReservations(new ReservationRequestDTO(requestDTO.getUserId(), new ArrayList<>(uniqueBookIds)));
            return ResponseEntity.ok("Reservation(s) created successfully");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBooks() {
        try {
            String message = reservationService.returnBooks();
            return ResponseEntity.ok(message);
        } catch (Exception e){
            String errorMessage="Failed to return books: "+ e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
