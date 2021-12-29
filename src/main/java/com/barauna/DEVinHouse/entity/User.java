package com.barauna.DEVinHouse.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

    private String login;
    private String password;
    private Set<String> roles = new HashSet<>();

//    public User(String login, String password) {
//        Set<String> roles = new HashSet<>();
//        roles.add("USER");
//        this(login, password, roles);
//    }

    public User(String login, String password, Set<String> roles) {
        this.login = login;
        this.password = password;
        this.roles.addAll(roles);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
