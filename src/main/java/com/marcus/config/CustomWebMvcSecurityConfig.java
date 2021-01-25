package com.marcus.config;

import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;

/**
 * @EnableWebMvcSecurity - deprecated
 * - contains @EnableWebSecurity via WebMvcSecurityConfiguration hence it does
 *   everything @EnableWebSecurity does plus more
 * - If you look at WebMvcSecurityConfiguration, you will see that it adds an
 *   AuthenticationPrincipalArgumentResolver so that you can access the authentication
 *   principal by adding an annotation to a controller method argument:
 *   public String show(@AuthenticationPrincipal CustomUser customUser) {
 *     // do something with CustomUser
 *     return "view";
 *   }
 */
@EnableWebMvcSecurity
public class CustomWebMvcSecurityConfig extends WebMvcSecurityConfiguration {
}
