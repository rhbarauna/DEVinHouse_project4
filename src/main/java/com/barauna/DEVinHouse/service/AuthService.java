package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.entity.User;
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

    public AuthService(PasswordEncoder passwordEncoder, UserService userService, UserDetailsServiceImpl userDetailsService, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    public UserDetailsImpl authenticated(){
        return this.userDetailsService.authenticated();
    }

    public void sendNewPass(String email)  {
        User user = userService.getUser(email);
        if(user == null) {
            throw new RuntimeException("Email not found.");
        }
        String newPass = new String(PasswordUtils.generatePassword(12));
        String encodePass = passwordEncoder.encode(newPass);
        user.setPassword(encodePass);
        userService.updateUser(user);
        emailService.sendNewPassword(email, newPass);
    }
}
