package com.barauna.DEVinHouse.repository;

import com.barauna.DEVinHouse.entity.Villager;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class VillagerRepository {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private List<Villager> villagers = Arrays.asList(
        new Villager(1, "Villager 1", "surname", "100.000.000-00", LocalDate.of(1941, Month.MARCH, 19), 10f),
        new Villager(2, "Villager 2", "surname", "200.000.000-00", LocalDate.of(1953, Month.APRIL, 29), 20f),
        new Villager(3, "Villager 3", "surname", "300.000.000-00", LocalDate.of(1983, Month.APRIL, 16), 40f),
        new Villager(4, "Villager 4", "surname", "040.000.000-00", LocalDate.of(1985, Month.JULY, 10), 30f)
    );

    public List<Villager> all(){
        return villagers;
    }

    public Optional<Villager> find(Integer villagerId) {
        return villagers.stream().filter(villager -> villager.getId().equals(villagerId)).findFirst();
    }

    public List<Villager> getByName(String villagerName) {
        return villagers.stream()
                .filter(
                        villager -> villager.getName().contains(villagerName)
                ).collect(Collectors.toList());
    }

    public List<Villager> getByBirthMonth(String villagerBirthMonth) {
        return villagers.stream()
                .filter(
                    villager -> {
                        //TODO - buscar por mes
                        return true;
                    }
                ).collect(Collectors.toList());
    }

    public List<Villager> getByAge(Integer age) {
        return villagers.stream()
                .filter(villager -> Period.between(villager.getBirthday(), LocalDate.now()).getYears() >= age).collect(Collectors.toList());
    }

    public void delete(Integer villagerId) {
        villagers.removeIf(villager -> villager.getId().equals(villagerId));
    }

    public Villager store(String name, String surName, String birthday, String document, Float wage) {
        final Integer id = villagers.get(villagers.size()-1).getId() + 1;
        final Villager newVillager = new Villager(id, name, surName, document, LocalDate.parse(birthday), wage);
        villagers.add(newVillager);
        return newVillager;
    }
}