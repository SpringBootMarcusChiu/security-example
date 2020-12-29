package com.marcus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 * @EnableWebSecurity
 * - annotation is crucial if we disable the default Spring Boot security configuration
 * - if missing, the application will fail to start. The annotation is only optional if
 *   we're just overriding the default behavior using a WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * need to use the PasswordEncoder to set the passwords when using Spring Boot 2
     * For more details, see our guide on the Default Password Encoder in Spring Security 5:
     * - https://www.baeldung.com/spring-security-5-default-password-encoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    ///////////////
    // AUTOWIRED //
    ///////////////

    /*
    There are 2 ways to configure AuthenticationManagerBuilder:
    - global: via @Autowired
    - local: via @Override
     */

    /**
     * Global AuthenticationManagerBuilder
     * @param auth
     * @throws Exception
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1").password(passwordEncoder().encode("password")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
//    }

    //////////////
    // OVERRIDE //
    //////////////

    /**
     * Local Anonymous AuthenticationManagerBuilder
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 1 - In Memory Authentication
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("user1").password(passwordEncoder().encode("password")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");

        // 2 - JDBC Authentication
//        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
//                .withDefaultSchema() // creates 2 tables: Users & Authorities
//                .withUser("user1").password(passwordEncoder().encode("password")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");

        // 3 - UserDetailsService
//        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    /**
     * role admin allow to access /admin/**
     * role user allow to access /user/**
     * custom 403 access denied handler
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
                // Control How Spring Security Creates Session
                // - ALWAYS – a session will always be created if one doesn't already exist
                // - IF_REQUIRED – a session will be created only if required (default)
                // - NEVER – the framework will never create a session itself but it will use one if it already exists
                // - STATELESS – no session will be created or used by Spring Security
                // This configuration only controls what Spring Security does – not the entire application
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // Session Fixation Protection
                // configuring what happens to an existing session when the user tries to authenticate again
                .sessionFixation()
                    .migrateSession() // (default) on authentication a new HTTP Session is created, the old one is invalidated and the attributes from the old session are copied over
//                    .newSession() // a clean session will be created without any of the attributes from the old session being copied over
//                    .none() // the original session will not be invalidated
//                    .changeSessionId() // changes the current session id with a new one,

            .and()

            //
            .csrf().disable().authorizeRequests()
            // the more specific rules need to come first, followed by the more general ones
            .antMatchers("/", "/home", "/about").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()

            .and()

            // LOGIN STUFF
//            .formLogin().loginPage("/login").permitAll()
//            .and()
//            .logout().permitAll();
            .formLogin()
            .loginPage("/login") // the custom login page
            // The default URL where the Spring Login will POST to trigger the authentication
            // process is /login which used to be /j_spring_security_check before Spring Security 4.
            // We can use the loginProcessingUrl() method to override this URL
            .loginProcessingUrl("/perform_login") // the URL to submit the username and password to
            // After a successful login process, the user is redirected to a page – which by default
            // is the root of the web application.
            // We can override this via the defaultSuccessUrl() method
            .defaultSuccessUrl("/home", true) // the landing page after a successful login
            // Same as with the Login Page, the Login Failure Page is autogenerated by Spring
            // Security at /login?error by default.
            // To override this, we can use the failureUrl() method
            .failureUrl("/login?error=true") // the landing page after an unsuccessful login
            //.failureHandler(authenticationFailureHandler()) //

            .and()

            // LOGOUT STUFF
            // By default, a logout request:
            // - invalidates the session
            // - clears any authentication caches
            // - clears the SecurityContextHolder
            // - redirects to login page
            // Simple Logout Config
            .logout(); // simple logout config
            // Custom Logout Config
//            .logout().logoutUrl("/my/logout")
//                .logoutSuccessUrl("/my/index")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear);
    }

//    /**
//     * Spring Boot has a default configuration for this already.
//     * @param web
//     * @throws Exception
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }
}
