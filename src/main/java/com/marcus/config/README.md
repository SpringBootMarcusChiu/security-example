### @Autowired AuthenticationManager vs @Override configure(AuthenticationManagerBuilder auth)
- https://github.com/spring-projects/spring-security/issues/4571
using @Autowired
```
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {
     
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("USER");
    }
}
```
using @Override
```
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {
     
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("admin").password("admin").roles("USER");
    }
}
```
configureGlobal() makes the `AuthenticationManager` available to the entire application (i.e. other `WebSecurityConfigurerAdapter` instances, `GlobalMethodSecurityConfiguration` instance, etc)

The protected configure is like an anonymous inner bean where the scope is limited to that of this `WebSecurityConfigurerAdapter`.

If you need it exposed as a Bean, you can use `authenticationManagerBean`.

Alternatively, you can also just expose one of a `UserDetailsService`, `AuthenticationProvider`, or `AuthenticationManger` as a Bean

### AuthenticationManager vs AuthenticationProvider
Both `AuthenticationManager` and `AuthenticationProvider` are interfaces

the `AuthenticationManager` delegates the fetching of persistent user information to one or more `AuthenticationProvider`s. The authentication-providers specialize in accessing specific user-info repositories.

some `AuthenticationProvider` examples:
- DaoAuthenticationProvider
- JaasAuthenticationProvider
- LdapAuthenticationProvider
- OpenIDAuthenticationProvider

In other words, you can specify multiple AuthenticationProviders, for example one that looks for users in an LDAP database and another that looks in an SQL database

resources:
- https://stackoverflow.com/questions/2323377/spring-security-authenticationmanager-vs-authenticationprovider
- https://www.javainuse.com/webseries/spring-security-jwt/chap3
