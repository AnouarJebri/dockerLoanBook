package com.esprit.frontendservice.controller;


import com.esprit.frontendservice.dto.UserDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class FrontendController {
    @GetMapping("")
    public String show(){
        return "home_page";
    }
   @GetMapping("/login")
    public String welcome(Model model){
       model.addAttribute("userDTO", new UserDTO());
        return "login";
   }
    @GetMapping("/homeA")
    public String homeA(){
        return "homeA";
    }
    @GetMapping("/homeS")
    public String homeS(){
        return "homeS";
    }
    @GetMapping("/register")
    public String signUP(){
        return "register";
    }
    @GetMapping("/loginError")
    public String loginError(){
        return "loginError";
    }

}
