package com.esprit.ConfigServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testcontroller {
    @GetMapping("/hi")
    public String resp(){
        return "hello";
    }
}
