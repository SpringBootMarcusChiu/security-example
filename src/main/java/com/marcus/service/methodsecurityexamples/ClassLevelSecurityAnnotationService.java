package com.marcus.service.methodsecurityexamples;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @PreAuthorize("hasRole('ROLE_ADMIN')")
 * - class level security annotation
 * - the security rule hasRole(‘ROLE_ADMIN') will be applied to both
 *   getSystemYear and getSystemDate methods
 */
@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ClassLevelSecurityAnnotationService {

    public String getSystemYear() {
        return "year";
    }

    public String getSystemDate() {
        return "date";
    }
}
