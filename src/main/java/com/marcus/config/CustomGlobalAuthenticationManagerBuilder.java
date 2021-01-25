package com.marcus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("levite").password("password").roles("USER")
                .and().withUser("god").password("password").roles("USER", "ADMIN");
    }
}