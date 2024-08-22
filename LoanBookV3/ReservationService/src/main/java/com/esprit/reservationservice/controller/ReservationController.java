package com.esprit.reservationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @GetMapping("/test")
    public String test(){
        return "well maven is now dockerized !";
    }
    @GetMapping("/reservations")
    public String reservations(){
        return "";
    }
}
