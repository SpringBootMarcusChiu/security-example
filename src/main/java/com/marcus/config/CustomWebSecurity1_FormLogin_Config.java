package com.marcus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Form Login Example
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 * @EnableWebSecurity
 * - annotation is crucial if we disable the default Spring Boot security configuration
 * - if missing, the application will fail to start. The annotation is only optional if
 *   we're just overriding the default behavior using a WebSecurityConfigurerAdapter
 * - contains @EnableGlobalAuthentication (see: CustomGlobalAuthenticationManagerBuilder)
 */
@Order(2147483647)
@Configuration
//@EnableWebSecurity //@EnableGlobalAuthentication
public class CustomWebSecurity1_FormLogin_Config extends WebSecurityConfigurerAdapter {

//    There are 2 ways to configure AuthenticationManagerBuilder:
//    - global: via @Autowired
//    - local: via @Override

    @Autowired // Global AuthenticationManagerBuilder
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // may add additional configuration to existing Global AuthenticationManagerBuilder
        // see CustomGlobalAuthenticationManagerBuilder.java
    }

    @Override // Local Anonymous AuthenticationManagerBuilder
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure Local AuthenticationManagerBuilder which is only applied within this class
        // the configuration applied to the Global can also be applied here
    }

    @Override // Custom 403 access denied handler
    protected void configure(HttpSecurity http) throws Exception {
        http

            // SESSION MANAGEMENT
            .sessionManagement()
                // Control How Spring Security Creates Session
                // - ALWAYS – a session will always be created if one doesn't already exist
                // - IF_REQUIRED – a session will be created only if required (default)
                // - NEVER – the framework will never create a session itself but it will use one if it already exists
                // - STATELESS – no session will be created or used by Spring Security
                // This configuration only controls what Spring Security does – not the entire application
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)


                // SESSION FIXATION PROTECTION
                // configuring what happens to an existing session when the user tries to authenticate again
                .sessionFixation()
                    .migrateSession() // (default) on authentication a new HTTP Session is created, the old one is invalidated and the attributes from the old session are copied over
//                    .newSession() // a clean session will be created without any of the attributes from the old session being copied over
                // use none() when using both HTTPS and HTTP. see: https://www.baeldung.com/spring-channel-security-https#2-disabling-session-fixation-protection
//                    .none() // the original session will not be invalidated
//                    .changeSessionId() // changes the current session id with a new one,
            .and()



            // CROSS-SITE REQUEST FORGERY (CSRF) - https://www.baeldung.com/spring-security-csrf
            // - CSRF protection is enabled by default in the Java configuration. We can still disable
            //   it if we need to with .csrf().disable()
//            .csrf().disable()



            // AUTHORIZATION
            .authorizeRequests()
            // The more specific rules need to come first, followed by the more general ones
                // allows users (including unauthenticated users) to access to the URLs
                // you could also place in WebSecurity.ignore.antMatchers however this will cause ${#httpServletRequest.remoteUser} to be null
                .antMatchers("/", "/home", "/about", "/webjars/bootstrap/**").permitAll()
                // allows users to access without authentication
                .antMatchers("/anon").anonymous()
                // only users with role ADMIN can access /admin/**
                .antMatchers("/admin/**").hasRole("ADMIN")
                // only users with either roles USER or ADMIN can access /user/**
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                // All remaining URLs require that the user be successfully authenticated
                .anyRequest().authenticated()
            .and()



            // BASIC LOGIN / LOGOUT
//            .formLogin().loginPage("/login").permitAll()
//            .and()
//            .logout().permitAll();

            // LOGIN STUFF
            // see: https://www.baeldung.com/spring-security-login
            // .formLogin() sets up form based authentication using the Java configuration defaults.
            // Authentication is performed when a POST is submitted to the URL “/login” with the
            // parameters “username” and “password”
            .formLogin()
                .loginPage("/login").permitAll() // the custom login page
                // The default URL where the Spring Login will POST to trigger the authentication
                // process is /login which used to be /j_spring_security_check before Spring Security 4.
                // We can use the loginProcessingUrl() method to override this URL
//                .loginProcessingUrl("/perform_login") // the URL to submit the username and password to
                // After a successful login process, the user is redirected to a page – which by default
                // is the root of the web application.
                // We can override this via the defaultSuccessUrl() method
//                .defaultSuccessUrl("/home", true).permitAll() // the landing page after a successful login
                // Same as with the Login Page, the Login Failure Page is autogenerated by Spring
                // Security at /login?error by default.
                // To override this, we can use the failureUrl() method
//                .failureUrl("/login?error=true").permitAll() // the landing page after an unsuccessful login
                //.failureHandler(authenticationFailureHandler()) //

            .and()

            // LOGOUT STUFF
            // By default, a logout request:
            // - invalidates the session
            // - clears any authentication caches
            // - clears the SecurityContextHolder
            // - redirects to login page
            // Simple Logout Config
            .logout().permitAll(); // simple logout config
            // Custom Logout Config
//                .logoutUrl("/logout");
//                .logoutSuccessUrl("/my/index")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear);



        // HTTPS SECURING SSL/TLS
        // this below requires /login* to be accessed only through HTTPS
//        http.requiresChannel()
//                .antMatchers("/login*")
//            .requiresSecure();

        // this below instructs Spring to use HTTP for all requests that are not explicitly
        // configured to use HTTPS, but at the same time it breaks the original login mechanism
        http.requiresChannel()
                .anyRequest().requiresInsecure();
    }
}
