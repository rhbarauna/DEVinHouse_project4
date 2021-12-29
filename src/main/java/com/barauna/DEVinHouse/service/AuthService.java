package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.security.UserDetailsImpl;
import com.barauna.DEVinHouse.utils.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthService(PasswordEncoder passwordEncoder, UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    public UserDetailsImpl authenticated(){
        return this.userDetailsService.authenticated();
    }

    public void sendNewPass(String email)  {
        User user = userService.getUser(email);
        if(user == null) {
            throw new RuntimeException("Email not found.");
        }
        String newPass = generatePassword();
        String encodePass = passwordEncoder.encode(newPass);
        user.setPassword(encodePass);
        userService.updateUser(user);
//        emailService.sendNewPassword(user, newPass);
    }

    private String generatePassword() {
        return new String(StringUtils.generatePassword(12));
    }
}
