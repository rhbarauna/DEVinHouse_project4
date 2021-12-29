package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class VillageService {

    @Value("${village.budget}")
    private Float villageBudget;

    private final VillagerService villagerService;

    public VillageService(VillagerService villagerService) {
        this.villagerService = villagerService;
    }

    public List<Villager> getVillagers() throws SQLException {
        return villagerService.getVillagers();
    }

    public Float getTotalCost() throws SQLException {
        return getVillagers().stream().reduce(
                villageBudget,(accumulator, villager) -> accumulator - villager.getWage(),
                Float::sum
        );
    }

    public Optional<Villager> getBiggestVillagerCost() throws SQLException {
        return getVillagers().stream().max(Villager.compareByCost);
    }

    public Float getInitialBudget() {
        return this.villageBudget;
    }

    public Float getVillageTotalCost() throws SQLException {
        return getVillagers().stream().reduce(
            0F,(accumulator, villager) -> accumulator + villager.getWage(),
            Float::sum
        );
    }
}
