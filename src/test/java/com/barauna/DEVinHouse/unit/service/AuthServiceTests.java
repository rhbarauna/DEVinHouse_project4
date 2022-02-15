package com.barauna.DEVinHouse.unit.service;

import com.barauna.DEVinHouse.security.UserDetailsImpl;
import com.barauna.DEVinHouse.service.AuthService;
import com.barauna.DEVinHouse.service.EmailService;
import com.barauna.DEVinHouse.service.UserDetailsServiceImpl;
import com.barauna.DEVinHouse.service.UserService;
import com.barauna.DEVinHouse.utils.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTests {
    private AuthService authService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsService;
    private EmailService emailService;
    private PasswordUtils passwordUtils;


    @BeforeEach
    public void beforeAll() {
        this.userService = mock(UserService.class);
        this.passwordEncoder = mock(PasswordEncoder.class);
        this.userDetailsService = mock(UserDetailsServiceImpl.class);
        this.emailService = mock(EmailService.class);
        this.passwordUtils = mock(PasswordUtils.class);
        this.authService = new AuthService(passwordEncoder, userService, userDetailsService, emailService, passwordUtils);
    }

    @Test
    void testAuthenticatedMethodReturningSuccessfully() {
        final UserDetailsImpl mockedResponse = new UserDetailsImpl("", "", new HashSet<String>());
        when(userDetailsService.authenticated()).thenReturn(mockedResponse);

        final UserDetailsImpl authenticated = authService.authenticated();
        verify(userDetailsService, atLeastOnce()).authenticated();
        assertNotNull(authenticated);
    }

    @Test
    void testAuthenticatedMethodReturningNullOnAuthFail() {
        when(userDetailsService.authenticated()).thenReturn(null);
        final UserDetailsImpl authenticated = authService.authenticated();
        assertNull(authenticated);
    }

    @Test
    void testSendEmailWithNewPassword() throws Exception{
        final String newPass = "123deOliveira4";
        final String email = "teste@teste.com";
        final String encodedPass = "passwordhasheado";

        when(passwordUtils.generatePassword(12)).thenReturn(newPass.toCharArray());
        when(passwordEncoder.encode(newPass)).thenReturn(encodedPass);
        authService.sendNewPass(email);

        verify(passwordEncoder, atLeastOnce()).encode(newPass);
        verify(userService, atLeastOnce()).updatePassword(email, encodedPass);
        verify(emailService, atLeastOnce()).sendNewPassword(email, newPass);
    }
}
