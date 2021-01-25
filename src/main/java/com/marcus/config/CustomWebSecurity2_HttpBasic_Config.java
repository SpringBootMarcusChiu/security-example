package com.marcus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Http Basic Authentication Example
 * https://www.baeldung.com/spring-security-basic-authentication
 */
@Order(0)
@Configuration
@EnableWebSecurity
public class CustomWebSecurity2_HttpBasic_Config extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("password")).authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // see: https://www.baeldung.com/spring-security-basic-authentication
        http.antMatcher("/http-basic/**")
                .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
            .and()
                .httpBasic(); // use HTTP Basic as authentication
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
/*
curl -i --user username:password http://localhost:8080/http-basic/test
 */