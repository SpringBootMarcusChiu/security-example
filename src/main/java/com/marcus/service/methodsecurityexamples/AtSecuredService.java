package com.marcus.service.methodsecurityexamples;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AtSecuredService {

    /**
     * @Secured annotation is used to specify a list of roles on a method.
     * Hence, a user only can access that method if she has at least one of the specified roles
     *
     * Cannot be used with SpEL
     */

    /**
     * @Secured(“ROLE_VIEWER”) annotation defines that only users who have the role ROLE_VIEWER
     * are able to execute the getUsername method
     * @return
     */
    @Secured("ROLE_VIEWER")
    public String getUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    /**
     * only users with role ROLE_VIEWER or ROLE_EDITOR are able to execute the isValidUsername() method
     * @param username
     * @return
     */
    @Secured({"ROLE_VIEWER", "ROLE_EDITOR"})
    public boolean isValidUsername(String username) {
        return true;
    }


    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public void test() {}
}
