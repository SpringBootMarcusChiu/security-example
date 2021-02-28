package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Multiple HttpSecurity Example
 */
@Configuration
//@EnableWebSecurity
public class CustomWebSecurity3_MultipleHttpSecurity_Config {

//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // may add additional configuration to existing Global AuthenticationManagerBuilder
//        // see CustomGlobalAuthenticationManagerBuilder.java
//    }

    @Configuration
    @Order(102)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/web-security-3-multiple-http-security/api/**")
                    .authorizeRequests()
                        .anyRequest().authenticated()
                .and()
                    .csrf().disable() // allow non GET requests
                    .httpBasic(); // use HTTP Basic as authentication
        }
        // curl -i --user admin:password http://localhost:8080/web-security-3-multiple-http-security/api
        // curl -i --user admin:password http://localhost:8080/web-security-3-multiple-http-security/api -X POST
    }

    @Configuration
    @Order(103)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/web-security-3-multiple-http-security/**")
                    .authorizeRequests()
                        .anyRequest().authenticated()
                .and()
                    .formLogin(); // use default Spring login page as authentication
        }
        // following redirects to login page
        // curl -i --user admin:password http://localhost:8080/web-security-3-multiple-http-security
        // curl -i --user admin:password http://localhost:8080/web-security-3-multiple-http-security -X POST
    }
}
/*
1 Authentication is configured as normal

2 Create 1st instance of WebSecurityConfigurerAdapter that contains @Order to specify
  which WebSecurityConfigurerAdapter should be considered first.
3 The http.antMatcher("/api/**") states that this HttpSecurity will only be applicable to URLs
  that start with /api/

4 Create 2nd instance of WebSecurityConfigurerAdapter. If the URL does not start
  with /api/ this configuration will be used. This configuration is considered after
  ApiWebSecurityConfigurationAdapter since it has an @Order value after 1 (no @Order
  defaults to last).
5 antMatcher() is a method of HttpSecurity, it doesn't have anything to do with
  authorizeRequests(). Basically, http.antMatcher() tells Spring to only configure
  HttpSecurity if the path matches this pattern.
6 The authorizeRequests().antMatchers() is then used to apply authorization to one
  or more paths you specify in antMatchers(). Such as permitAll() or hasRole('USER').
  These only get applied if the first http.antMatcher() is matched
*/