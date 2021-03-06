package com.marcus.config;

/**
 * NOTE: This is a NON SPRING SECURITY file
 * REPLACED: by CustomWebMvcSecurity2_interface_Config.java
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
 * - It also integrates with Spring Web MVC to add a CSRF token to forms
 */
//@EnableWebMvcSecurity
//public class CustomWebMvcSecurityConfig extends WebMvcSecurityConfiguration {
//}
