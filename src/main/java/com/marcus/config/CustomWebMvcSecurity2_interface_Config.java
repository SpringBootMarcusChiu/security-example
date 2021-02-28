package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * NOTE: This is a NON SPRING SECURITY file
 * is a replacement of CustomWebMvcSecurity1_class_Config.java (bc of Java8's default methods)
 */
@Configuration
public class CustomWebMvcSecurity2_interface_Config implements WebMvcConfigurer {

    /**
     * can also be handled globally by creating a `CorsConfigurationSource` bean like in `CustomWebSecurity6_global_cors_Config.java`
     */
    @Override // Configuring CORS without Spring Security
    public void addCorsMappings(CorsRegistry registry) {
        // this allows all resources to be CORS
        // to allow authenticated resources add `http.cors().and()...` to HttpSecurity
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    /**
     * Caching static assets
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        // Another way to cache is adding the following into application.properties:
        // spring.resources.cache.cachecontrol.max-age=365d
        // However, this applies to all static resources served by Spring Boot

        // For `Versioning Static Assets` see
        // https://www.baeldung.com/cachable-static-assets-with-spring-mvc
    }
}
