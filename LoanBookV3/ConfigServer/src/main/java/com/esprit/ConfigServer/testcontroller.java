package com.esprit.ConfigServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class testcontroller {
    @GetMapping("/hi")
    public String resp(){
        return "hello";
    }
    @GetMapping("/test")
    public String req(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8888/authservice/default";
        return restTemplate.getForObject(url, String.class);
    }
}
