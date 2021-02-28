package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * is a replacement of CustomWebMvcSecurity1_class_Config.java (bc of Java8's default methods)
 */
@Configuration
public class CustomWebMvcSecurity2_interface_Config implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedOrigins("*")
//                .allowedHeaders("*");
//    }
}
