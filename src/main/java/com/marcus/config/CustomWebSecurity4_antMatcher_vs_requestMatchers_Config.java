package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * http.antMatcher
 * vs
 * http.requestMatchers
 */
@Configuration
//@EnableWebSecurity
public class CustomWebSecurity4_antMatcher_vs_requestMatchers_Config {

    @Configuration
    @Order(104)
    public static class Api1WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/v1/**")
                    .authorizeRequests()
                        .anyRequest().hasRole("ADMIN")
                .and()
                    .httpBasic();
        }
    }

    @Configuration
    @Order(105)
    public static class Api1And2WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers()
                        .antMatchers("/api/v1/**")
                        .antMatchers("/api/v2/**")
                .and()
                    .authorizeRequests()
                        .anyRequest().hasRole("ADMIN")
                .and()
                    .httpBasic();
        }
    }
}
/*
1 http.antMatcher("/api/v1/**") states this HttpSecurity only applies to /api/v1/**

2 http.requestMatchers()
    .antMatchers("/api/v1/**")
    .antMatchers("/api/v2/**")
  states this HttpSecurity only applies to both /api/v1/** & /api/v2/**
 */