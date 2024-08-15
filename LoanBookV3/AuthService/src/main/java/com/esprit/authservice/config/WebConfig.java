package com.esprit.authservice.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Getter
    public final static String frontendUrl="http://frontendservice/";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Configuring CORS"); // Log to ensure this method is called
        registry.addMapping("/**")
                .allowedOrigins(frontendUrl) // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // Allow credentials (cookies)
    }
}
