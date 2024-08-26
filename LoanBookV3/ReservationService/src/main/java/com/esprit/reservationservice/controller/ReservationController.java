package com.esprit.reservationservice.controller;

import com.esprit.reservationservice.DTO.ReservationRequestDTO;
import com.esprit.reservationservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        reservationService.createReservations(requestDTO);
        return ResponseEntity.ok("Reservation(s) created successfully");
    }
}
