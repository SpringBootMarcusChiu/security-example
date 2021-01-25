package com.marcus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * see: https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/config/annotation/authentication/configuration/EnableGlobalAuthentication.html
 * - @EnableGlobalAuthentication annotation signals that the annotated class can be used to configure
 *   a global instance of AuthenticationManagerBuilder with:
 *   public void configureGlobal(AuthenticationManagerBuilder auth) { ... }
 * - The following annotations are annotated with EnableGlobalAuthentication
 *   - EnableWebSecurity
 *   - EnableWebMvcSecurity
 *   - EnableGlobalMethodSecurity
 * - Configuring AuthenticationManagerBuilder in a class without the EnableGlobalAuthentication
 *   annotation has unpredictable results.
 */
@Configuration
@EnableGlobalAuthentication
public class CustomGlobalAuthenticationManagerBuilder {

    // defined in CustomWebSecurityConfig.java
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("levite").password(passwordEncoder.encode("password")).roles("USER")
                .and()
                .withUser("god").password(passwordEncoder.encode("password")).roles("USER", "ADMIN");
    }
}