package com.barauna.DEVinHouse.to;

import java.util.Set;

public class UserTO {

    private Long userId;
    private String email;
    private String password;
    private Long villagerId;
    private Set<String> roles;

    public UserTO(Long userId, String email, String password, Long villagerId, Set<String> roles) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.villagerId = villagerId;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getVillagerId() {
        return villagerId;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
