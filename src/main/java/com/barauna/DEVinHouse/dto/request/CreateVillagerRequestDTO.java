package com.barauna.DEVinHouse.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class CreateVillagerRequestDTO {

    private String name;
    private String surName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    private String document;
    private Float wage;
    private String email;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> roles;

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
    public Float getWage() {
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
