package com.marcus.service.methodsecurityexamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PrePostAuthorizedService {

    @Autowired
    AtSecuredService atSecuredService;

    /**
     * Both @PreAuthorize and @PostAuthorize annotations provide expression-based access control.
     * Hence, predicates can be written using SpEL (Spring Expression Language).
     *
     * @PreAuthorize
     * - annotation checks the given expression before entering the method
     * @PostAuthorize
     * - annotation verifies it after the execution of the method and could alter the result
     * - provides the ability to access the method result
     *
     * For more complex SpEL see:
     * https://www.baeldung.com/spring-security-create-new-custom-security-expression
     */

    /**
     * The @PreAuthorize(“hasRole(‘ROLE_VIEWER')”) has the same meaning as @Secured(“ROLE_VIEWER”)
     * which we used in the previous section
     * @return
     */
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public String getUsernameInUpperCase() {
        return atSecuredService.getUsername().toUpperCase();
    }

    /**
     * hasAuthority('VIEWER') == hasRole('ROLE_VIEWER')
     * @return
     */
    @PreAuthorize("hasAuthority('VIEWER')")
    public String getUsernameInUpperCase2() {
        return atSecuredService.getUsername().toUpperCase();
    }

    /**
     * the annotation @Secured({“ROLE_VIEWER”,”ROLE_EDITOR”}) can be replaced
     * with @PreAuthorize(“hasRole(‘ROLE_VIEWER') or hasRole(‘ROLE_EDITOR')”)
     * @param username
     * @return
     */
    @PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR')")
    public boolean isValidUsername3(String username) {
        return atSecuredService.isValidUsername(username);
    }

    /**
     * Use the method argument as part of the expression
     * User can invoke the getMyRoles method only if the value of the
     * argument username is the same as current principal's username
     * @param username
     * @return
     */
    @PreAuthorize("#username == authentication.principal.username")
    public String getMyRoles(String username) {
        return "roles";
    }

    /**
     * method would only execute successfully if the username of the returned
     * CustomUser is equal to the current authentication principal's nickname
     * @param username
     * @return
     */
    @PostAuthorize("returnObject.username == authentication.principal.nickName")
    public UserDetails loadUserDetail(String username) {
        return User.builder().username(username).build();
    }

    @PreAuthorize("isAnonymous()")
    public void test() {}
}
