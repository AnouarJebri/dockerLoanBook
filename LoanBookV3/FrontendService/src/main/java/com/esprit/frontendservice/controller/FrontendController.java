package com.esprit.frontendservice.controller;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/frontend")
public class FrontendController {
    @GetMapping("/default")
    public String show(){
        return "home_page";
    }
   @GetMapping("/login")
    public String welcome(){
       return "login";
   }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/"); // This is important, you may need to adjust the path
                response.addCookie(cookie);
            }
        }
        return "redirect:/login";
    }


    @GetMapping("/homeA")
    public String homeA(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    String userId = cookie.getValue();
                    // Create a session and set the userId attribute
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId", userId);
                    break;
                }
            }
        }
        return "homeA";
    }
    @GetMapping("/homeS")
    public String homeS(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    String userId = cookie.getValue();
                    // Create a session and set the userId attribute
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId", userId);
                    break;
                }
            }
        }
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
