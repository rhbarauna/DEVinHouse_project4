package com.barauna.DEVinHouse.controller;

import com.barauna.DEVinHouse.dto.request.MailRequestDTO;
import com.barauna.DEVinHouse.dto.response.JwtTokenDTO;
import com.barauna.DEVinHouse.security.UserDetailsImpl;
import com.barauna.DEVinHouse.service.AuthService;
import com.barauna.DEVinHouse.utils.JWTTokenUtils;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTTokenUtils jwtUtil;
    private final AuthService authService;

    public AuthController(JWTTokenUtils jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) throws IOException {
        UserDetailsImpl userSpringSecurity = authService.authenticated();
        JwtTokenDTO generatedToken = jwtUtil.generateToken(userSpringSecurity.getUsername());
        response.addHeader("Authorization", generatedToken.getFullToken());
        response.getWriter().append(new Gson().toJson(generatedToken));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@RequestBody MailRequestDTO mail){
        authService.sendNewPass(mail.getEmail());
        return ResponseEntity.noContent().build();
    }
}
