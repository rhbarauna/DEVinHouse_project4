package com.barauna.DEVinHouse.entity;


import java.util.Comparator;

public class Villager {
    private Integer id;
    private String name;
    private String surName;
    private Integer age;
    private Float wage;

    public Villager(Integer id, String name, String surName, Integer age, Float wage) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getWage() {
        return wage;
    }

    public void setWage(Float wage) {
        this.wage = wage;
    }

    public static final Comparator<Villager> compareByCost = (Villager v1, Villager v2) -> {
        return v1.getWage().compareTo(v2.getWage());
    };
}
