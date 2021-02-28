package com.marcus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(0)
@Configuration
//@EnableWebSecurity //@EnableGlobalAuthentication
public class CustomWebSecurity0_WebSecurity_Config extends WebSecurityConfigurerAdapter {

    @Override // Spring Boot has a default configuration for this already
    public void configure(WebSecurity web) throws Exception {
        web
                // the following URl patterns to bypass Spring Security
                .ignoring()
                .antMatchers("/not-default/**") // resources/static/pokemon/**
                .antMatchers("/resources/**")
//                .antMatchers("/user/**") // this will cause ${#httpServletRequest.remoteUser} to be null
//                .antMatchers("/admin/**") // this will cause ${#httpServletRequest.remoteUser} to be null
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/images/**");
//                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
        //                .requestMatchers(EndpointRequest.to("health"));
    }
}
