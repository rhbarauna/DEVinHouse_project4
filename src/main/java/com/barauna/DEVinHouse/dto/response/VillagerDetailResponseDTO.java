package com.barauna.DEVinHouse.dto.response;

import java.time.LocalDate;
import java.util.Date;

public class VillagerDetailResponseDTO {
    private String name;
    private String surName;
    private LocalDate birthday;
    private String document;
    private Float wage;

    public VillagerDetailResponseDTO(String name, String surName, LocalDate birthday, String document, Float wage) {
        this.name = name;
        this.surName = surName;
        this.birthday = birthday;
        this.document = document;
        this.wage = wage;
    }

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
