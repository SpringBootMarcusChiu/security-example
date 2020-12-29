package com.marcus.service;

import com.marcus.config.GlobalMethodSecurityConfig;
import com.marcus.service.methodsecurityexamples.AtSecuredService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@Import({AtSecuredService.class, GlobalMethodSecurityConfig.class})
//@SpringBootTest
public class AtSecuredServiceTest {

    @Autowired
    AtSecuredService atSecuredService;

    @Test
    @WithMockUser(username = "john", roles = { "VIEWER" })
    public void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
        String userName = atSecuredService.getUsername();
        assertEquals("john", userName);
    }

    /**
     * same as above
     * @WithMockJohnViewer == @WithMockUser(username = "john", roles = { "VIEWER" })
     */
    @Test
    @WithMockJohnViewer
    public void givenRoleViewer_whenCallGetUsername_thenReturnUsername_2() {
        String userName = atSecuredService.getUsername();
        assertEquals("john", userName);
    }

    @Test
    @WithMockUser(username = "john", roles = {"SOME"})
    public void givenRoleNonViewer_whenCallGetUsername_thenThrowException() {
        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            atSecuredService.getUsername();
        });
    }

    @Test
    @WithAnonymousUser
    public void givenAnomynousUser_whenCallGetUsername_thenAccessDenied() {
        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            atSecuredService.getUsername();
        });
    }
}
