package com.barauna.DEVinHouse.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class CreateVillagerRequestDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private String surName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @NotEmpty
    @Pattern(regexp="\\^([0-9]{3}\\.?){3}-?[0-9]{2}\\$")
    private String document;

    private Float wage;

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
}
