package com.muyi.lukman.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AppConfig implements WebMvcConfigurer {
    //TODO: Angular APP deployed on server A
    //TODO: API deployed on server B
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // .allowedOrigins("http://localhost:8080/", "http://localhost:8080")
                .allowedOrigins("*")
                // .allowedMethods("POST", "GET",  "PUT", "DELETE")
                .allowedMethods("*")
                .allowedHeaders("*")
                //.allowedHeaders("X-Auth-Token", "Content-Type")
                //.exposedHeaders("custom-header1", "custom-header2")
                .allowCredentials(false)
                .maxAge(4800);
    }
}
