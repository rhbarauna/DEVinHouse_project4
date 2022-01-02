package com.barauna.DEVinHouse.to;

import java.util.Set;

public class UserTO {

    private String email;
    private String password;
    private Long villagerId;
    private Set<String> roles;

    public UserTO(String email, String password, Long villagerId, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.villagerId = villagerId;
        this.roles = roles;
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
