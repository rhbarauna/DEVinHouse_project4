package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public UserDetailsImpl authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new UserDetailsImpl((String) authentication.getPrincipal(), null,
                    authentication.getAuthorities());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(user.getLogin(), user.getPassword(), user.getRoles());

    }
}
