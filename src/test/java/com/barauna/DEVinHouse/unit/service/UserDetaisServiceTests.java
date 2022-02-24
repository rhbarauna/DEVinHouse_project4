package com.barauna.DEVinHouse.unit.service;


import com.barauna.DEVinHouse.service.UserDetailsServiceImpl;
import com.barauna.DEVinHouse.service.UserService;

import com.barauna.DEVinHouse.to.UserTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetaisServiceTests {
    UserService userService;
    UserDetailsServiceImpl userDetailService;

    @BeforeEach
    public void beforeEach() {
        this.userService = mock(UserService.class);
        userDetailService = new UserDetailsServiceImpl(this.userService);
    }

    @Test
    public void testLoadUserByEmailSuccessfully() throws Exception {
        UserTO userTO = new UserTO(1L, "email@email.com", "", 1L, Set.of("ADMIN"));
        when(userService.getUser("email@email.com")).thenReturn(userTO);
        final UserDetails userDetails = userDetailService.loadUserByUsername("email@email.com");

        assertNotNull(userDetails);
        assertEquals(userTO.getEmail(), userDetails.getUsername());
    }

    @Test
    public void testLoadUserByEmail() throws Exception {
        when(userService.getUser("email@email.com")).thenThrow(NoSuchElementException.class);
        assertThrows(UsernameNotFoundException.class, ()->userDetailService.loadUserByUsername("email@email.com"));
    }
}
