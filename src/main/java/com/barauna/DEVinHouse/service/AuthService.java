package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.security.UserDetailsImpl;
import com.barauna.DEVinHouse.utils.PasswordUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final EmailService emailService;
    private final PasswordUtils passwordUtils;

    public AuthService(PasswordEncoder passwordEncoder, UserService userService, UserDetailsServiceImpl userDetailsService, EmailService emailService, PasswordUtils passwordUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
        this.passwordUtils = passwordUtils;
    }

    public UserDetailsImpl authenticated(){
        return this.userDetailsService.authenticated();
    }

    public void sendNewPass(String email) throws Exception {
        String newPass = new String(passwordUtils.generatePassword(12));
        String encodePass = passwordEncoder.encode(newPass);
        userService.updatePassword(email, encodePass);
        emailService.sendNewPassword(email, newPass);
    }
}
