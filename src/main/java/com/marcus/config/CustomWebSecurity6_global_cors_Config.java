package com.marcus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Order(107)
@Configuration
//@EnableWebSecurity
public class CustomWebSecurity6_global_cors_Config extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/cors-global/**")
                .authorizeRequests()
                    .anyRequest()
                        // 1. Without Authorization
                        .permitAll()
                        // 2. With Authorization
//                            .authenticated()
            .and()
                // allows /cors-global/** to be accessed CORS
                // enables controller level @CrossOrigin annotations
                // enables Spring Security CORS support and it will leverage Spring MVC configuration
                .cors()
            .and()
                .csrf().disable() // allow all other NON GET requests
                .httpBasic();
    }
    // 1. Without Authorization
    // curl -i http://localhost:8080/cors-global
    // curl -i http://localhost:8080/cors-global -X POST

    // 2. With Authorization
    // curl -i --user user:password http://localhost:8080/cors-global
    // curl -i --user user:password http://localhost:8080/cors-global -X POST

    /**
     * A Replacement of controller level @CrossOrigin
     * method name must be `corsConfigurationSource` in order to be picked up by Spring Boot
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/cors-global/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
