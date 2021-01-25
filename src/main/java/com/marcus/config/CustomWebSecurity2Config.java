package com.marcus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Multiple HttpSecurity Example
 */
@EnableWebSecurity
public class CustomWebSecurity2Config {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .authorizeRequests()
                        .anyRequest().hasRole("ADMIN")
                .and()
                    .httpBasic(); // use HTTP Basic as authentication
        }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .anyRequest().authenticated()
                .and()
                    .formLogin(); // use default Spring login page as authentication
        }
    }
}
/*
1 Authentication is configured as normal

2 Create an instance of WebSecurityConfigurerAdapter that contains @Order to specify
  which WebSecurityConfigurerAdapter should be considered first.

3 The http.antMatcher("/api/**") states that this HttpSecurity will only be applicable to URLs
  that start with /api/

4 Create another instance of WebSecurityConfigurerAdapter. If the URL does not start
  with /api/ this configuration will be used. This configuration is considered after
  ApiWebSecurityConfigurationAdapter since it has an @Order value after 1 (no @Order
  defaults to last).

5 antMatcher() is a method of HttpSecurity, it doesn't have anything to do with
  authorizeRequests(). Basically, http.antMatcher() tells Spring to only configure
  HttpSecurity if the path matches this pattern.

6 The authorizeRequests().antMatchers() is then used to apply authorization to one
  or more paths you specify in antMatchers(). Such as permitAll() or hasRole('USER3').
  These only get applied if the first http.antMatcher() is matched
*/