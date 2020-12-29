package com.marcus.service.methodsecurityexamples;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

@Service
public class AtRolesAllowedService {

    /**
     * The @RoleAllowed annotation is the JSR-250’s equivalent annotation of the @Secured annotation
     */

    /**
     * only users with role ROLE_VIEWER can execute getUsername2() method
     * @return
     */
    @RolesAllowed("ROLE_VIEWER")
    public String getUsername2() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    /**
     * only users with role ROLE_VIEWER or ROLE_VIEWER can execute isValidUsername2() method
     * @param username
     * @return
     */
    @RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" })
    public boolean isValidUsername2(String username) {
        return true;
    }
}
