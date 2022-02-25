package com.barauna.DEVinHouse.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateVillagerRequestDTO {

    private String name;
    private String surName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String document;
    private BigDecimal wage;
    private String email;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> roles;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }
    public String getSurName() {
        return surName;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public String getDocument() {
        return document;
    }
    public BigDecimal getWage() {
        return wage;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
