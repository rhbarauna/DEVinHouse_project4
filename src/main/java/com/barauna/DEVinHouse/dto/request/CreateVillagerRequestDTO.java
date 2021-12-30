package com.barauna.DEVinHouse.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CreateVillagerRequestDTO {

    private String name;
    private String surName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    private String document;
    private Float wage;
    private String email;
    private String password;

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
}
