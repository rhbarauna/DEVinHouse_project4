package com.barauna.DEVinHouse.entity;

import java.util.Set;

public class User {

    private Long id;
    private String email;
    private String password;
    private Set<String> roles;
    private Long villagerId;

    public User(String email, String password, Set<String> roles, Long villagerId) {
        this.email = email;
        this.password = password;
        this.roles= roles;
        this.villagerId = villagerId;
    }

    public User(Long id, Long villagerId, String email, String password, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.villagerId = villagerId;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getVillagerId() {
        return villagerId;
    }

    public void setVillagerId(Long villagerId) {
        this.villagerId = villagerId;
    }
}
