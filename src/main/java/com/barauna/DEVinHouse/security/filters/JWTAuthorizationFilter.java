package com.barauna.DEVinHouse.security.filters;

import com.barauna.DEVinHouse.service.UserDetailsServiceImpl;
import com.barauna.DEVinHouse.utils.JWTTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private JWTTokenUtils jwtUtil;
    private UserDetailsService userService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTTokenUtils jwtUtil,
                                  UserDetailsService userService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader("Authorization");
        validateToken(header);
        chain.doFilter(request, response);
    }

    private void validateToken(String header) {
        if (header == null) {
            return;
        }

        if (!header.startsWith("Bearer ")) {
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                getAuthentication(header.substring(7));

        if (usernamePasswordAuthenticationToken == null) {
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (!jwtUtil.validateToken(token)) {
            return null;
        }

        String username = jwtUtil.getTokenSubject(token);
        UserDetails user = userService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
    }
}
