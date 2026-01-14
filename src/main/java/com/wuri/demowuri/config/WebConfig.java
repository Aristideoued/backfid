package com.wuri.demowuri.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200","http://192.168.11.141:4200") // URLs autorisées
                .allowedMethods("GET", "POST", "PUT", "DELETE","UPDATE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
