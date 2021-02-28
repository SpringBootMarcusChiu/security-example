package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(106)
@Configuration
//@EnableWebSecurity
public class CustomWebSecurity5_local_cors_Config extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/cors-local/**")
                .authorizeRequests()
                    .anyRequest()
                        // 1. Without Authorization
//                            .permitAll()
                        // 2. With Authorization
                        .authenticated()
            .and()
                // allows /cors-local/** to be accessed CORS
                // enables controller level @CrossOrigin annotations
                // enables Spring Security CORS support and it will leverage Spring MVC configuration
                // - By default uses a Bean by the name of `corsConfigurationSource`
                //   If Spring MVC is on classpath and no CorsConfigurationSource is provided,
                //   Spring Security will use CORS configuration provided to Spring MVC
                //   or configured in `addCorsMappings(CorsRegistry registry)` in `CustomWebMvcSecurity2_interface_Config.java`
                .cors()
            .and()
                .csrf().disable() // allow all other NON GET requests
                .httpBasic();
    }
    // 1. Without Authorization
    // curl -i http://localhost:8080/cors-local
    // curl -i http://localhost:8080/cors-local -X POST

    // 2. With Authorization
    // curl -i --user user:password http://localhost:8080/cors-local
    // curl -i --user user:password http://localhost:8080/cors-local -X POST
}
