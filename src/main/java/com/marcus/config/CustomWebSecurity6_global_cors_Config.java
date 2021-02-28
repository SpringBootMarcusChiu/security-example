package com.marcus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Order(107)
@Configuration
//@EnableWebSecurity
public class CustomWebSecurity6_global_cors_Config extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/cors-global/**")
                .authorizeRequests()
                    .anyRequest()
                        // 1. Without Authorization
                        .permitAll()
                        // 2. With Authorization
//                            .authenticated()
            .and()
                // allows /cors-global/** to be accessed CORS
                // enables controller level @CrossOrigin annotations
                // enables Spring Security CORS support and it will leverage Spring MVC configuration
                // - By default uses a Bean by the name of `corsConfigurationSource`
                //   If Spring MVC is on classpath and no CorsConfigurationSource is provided,
                //   Spring Security will use CORS configuration provided to Spring MVC
                .cors()
//                    .configurationSource(corsConfigurationSource()) // pass in custom CorsConfigurationSource
            .and()
                .csrf().disable() // allow all other NON GET requests
                .httpBasic();
    }
    // 1. Without Authorization
    // curl -i http://localhost:8080/cors-global
    // curl -i http://localhost:8080/cors-global -X POST

    // 2. With Authorization
    // curl -i --user user:password http://localhost:8080/cors-global
    // curl -i --user user:password http://localhost:8080/cors-global -X POST

    /**
     * A Replacement of controller level @CrossOrigin
     * method name must be `corsConfigurationSource` in order to be picked up by Spring
     * This whole `CorsConfigurationSource` can be replaced by `addCorsMappings(CorsRegistry registry)` in `CustomWebMvcSecurity2_interface_Config.java`
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/cors-global/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
    }
}
