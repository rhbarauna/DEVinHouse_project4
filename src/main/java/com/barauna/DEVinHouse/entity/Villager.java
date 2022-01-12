package com.barauna.DEVinHouse.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Comparator;

@Entity
public class Villager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "surname")
    private String surName;
    private Float wage;
    private LocalDate birthday;
    private String document;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    private User user;

    public Villager(){}

    public Villager(String name, String surName, String document, LocalDate birthday, Float wage) {
        this.name = name;
        this.surName = surName;
        this.birthday = birthday;
        this.document = document;
        this.wage = wage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static final Comparator<Villager> compareByCost = (Villager v1, Villager v2) -> {
        return v1.getWage().compareTo(v2.getWage());
    };
}
