package com.marcus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * see: https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/config/annotation/authentication/configuration/EnableGlobalAuthentication.html
 * - @EnableGlobalAuthentication annotation signals that the annotated class can be used to configure
 *   a global instance of AuthenticationManagerBuilder with:
 *   public void configureGlobal(AuthenticationManagerBuilder auth) { ... }
 * - The following annotations are annotated with EnableGlobalAuthentication
 *   - EnableWebSecurity    - see CustomWebSecurity1_FormLogin_Config.java
 *   - EnableWebMvcSecurity - deprecated see CustomWebMvcSecurityConfig.java
 *   - EnableGlobalMethodSecurity - see CustomGlobalMethodSecurityConfig.java
 * - Configuring AuthenticationManagerBuilder in a class without the EnableGlobalAuthentication
 *   annotation has unpredictable results.
 */
@Configuration
@EnableGlobalAuthentication
public class CustomGlobalAuthenticationManagerBuilder {

    /**
     * need to use the PasswordEncoder to set the passwords when using Spring Boot 2
     * For more details, see our guide on the Default Password Encoder in Spring Security 5:
     * - https://www.baeldung.com/spring-security-5-default-password-encoder
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure Global AuthenticationManagerBuilder
        // there are 3 ways:
        // - in memory authentication
        // - JDBC Authentication - against any database (external or embedded)
        // - UserDetailsService

        // 1 - In Memory Authentication
        auth.inMemoryAuthentication()
                .withUser("levite").password(passwordEncoder().encode("password")).roles("USER").and()
                .withUser("god").password(passwordEncoder().encode("password")).roles("USER", "ADMIN").and()
                .withUser("user").password(passwordEncoder().encode("password")).roles("USER").and()
                .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
//                .and()
//                .withUser("user").password("password").roles("USER").and()
//                .withUser("admin").password("password").roles("USER", "ADMIN");

        // 2 - JDBC Authentication
//        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
//                .withDefaultSchema() // creates 2 tables: Users & Authorities
//                .withUser("user1").password(passwordEncoder().encode("password")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");

        // 3 - UserDetailsService
//        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
}