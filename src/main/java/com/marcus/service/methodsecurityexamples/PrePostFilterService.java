package com.marcus.service.methodsecurityexamples;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PrePostFilterService {

    /*
     * @PreFilter
     * - annotation to filter the collection argument before executing the method
     * @PostFilter
     * - annotation to filter the returned collection of a method
     */

    /**
     * we're joining all usernames except for the one who is authenticated
     * @param usernames
     * @return
     */
    @PreFilter("filterObject != authentication.principal.username")
    public String joinUsernames(List<String> usernames) {
        return String.join(";", usernames);
    }

    /**
     * if the method has more than one argument which is a collection type, we need
     * to use the filterTarget property to specify which argument we want to filter
     * @param usernames
     * @param roles
     * @return
     */
    @PreFilter(value = "filterObject != authentication.principal.username", filterTarget = "usernames")
    public String joinUsernamesAndRoles(List<String> usernames, List<String> roles) {
        return String.join(";", usernames) + ":" + String.join(";", roles);
    }

    /**
     * the name filterObject refers to the current object in the returned collection
     * Spring Security will iterate through the returned list and remove any value
     * matching the principal's username
     * @return
     */
    @PostFilter("filterObject != authentication.principal.username")
    public List<String> getAllUsernamesExceptCurrent() {
        return Arrays.asList("marcus-chiu", "erina-chiu");
    }
}
