package com.barauna.DEVinHouse.entity;


import java.util.Comparator;
import java.util.Date;

public class Villager {
    private Integer id;
    private String name;
    private String surName;
    private Float wage;
    private Date birthday;
    private String document;


    public Villager(Integer id, String name, String surName, String document, Date birthday, Float wage) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.birthday = birthday;
        this.document = document;
        this.wage = wage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getWage() {
        return wage;
    }

    public void setWage(Float wage) {
        this.wage = wage;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public static final Comparator<Villager> compareByCost = (Villager v1, Villager v2) -> {
        return v1.getWage().compareTo(v2.getWage());
    };
}
