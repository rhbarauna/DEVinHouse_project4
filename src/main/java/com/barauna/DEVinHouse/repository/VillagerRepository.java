package com.barauna.DEVinHouse.repository;

import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VillagerRepository {

    private List<Villager> villagers = Arrays.asList(
            new Villager(1, "Villager 1", "surname", 1, 10f),
            new Villager(2, "Villager 2", "surname", 2, 20f),
            new Villager(3, "Villager 3", "surname", 2, 40f),
            new Villager(4, "Villager 4", "surname", 2, 30f)
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
}
