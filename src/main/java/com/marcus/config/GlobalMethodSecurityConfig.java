package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @EnableGlobalMethodSecurity
 * - prePostEnabled property enables Spring Security pre/post annotations (e.g. @PreFilter/@PostFilter/@PreAuthorized/@PostAuthorized)
 * - securedEnabled property determines if the @Secured annotation should be enabled
 * - jsr250Enabled property allows us to use the JSR-250 @RoleAllowed annotation
 */
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
