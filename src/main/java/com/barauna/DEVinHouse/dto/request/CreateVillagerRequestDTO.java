package com.barauna.DEVinHouse.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

//TODO - add validation using jsonformat?
public class CreateVillagerRequestDTO {
    private String name;
    private String surName;
    //TODO - create a custom date serializer https://www.baeldung.com/jackson-serialize-dates
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    private String document;
    private Float wage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Float getWage() {
        return wage;
    }

    public void setWage(Float wage) {
        this.wage = wage;
    }
}
