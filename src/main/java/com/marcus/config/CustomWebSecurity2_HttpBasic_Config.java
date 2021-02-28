package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Http Basic Authentication Example
 * https://www.baeldung.com/spring-security-basic-authentication
 */
@Order(100)
//@EnableWebSecurity
@Configuration
public class CustomWebSecurity2_HttpBasic_Config extends WebSecurityConfigurerAdapter {

//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // may add additional configuration to existing Global AuthenticationManagerBuilder
//        // see CustomGlobalAuthenticationManagerBuilder.java
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // see: https://www.baeldung.com/spring-security-basic-authentication
        http.antMatcher("/web-security-2-http-basic/**")
                .authorizeRequests()
                    .anyRequest().hasRole("USER")
            .and()
                .csrf().disable() // allow non GET requests
                .httpBasic(); // use HTTP Basic as authentication
    }
}
/*
curl -i --user user:password http://localhost:8080/web-security-2-http-basic
curl -i --user user:password http://localhost:8080/web-security-2-http-basic -X POST
 */