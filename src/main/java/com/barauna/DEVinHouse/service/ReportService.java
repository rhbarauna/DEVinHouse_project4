package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.dto.response.ReportResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReportService {

    private Float villageBudget;
    private VillagerService villagerService;

    public ReportService(@Value("${village.budget}") Float villageBudget, VillagerService villagerService) {
        this.villageBudget = villageBudget;
        this.villagerService = villagerService;
    }

    public ReportResponseDTO generate() throws SQLException {
        final List<Villager> villagers = villagerService.getVillagers();
        final Float initialBudget = villageBudget;
        final Float villageTotalCost = villagers.stream().reduce(
                0F,(accumulator, villager) -> accumulator + villager.getWage(),
                Float::sum
        );
        final Float cost = initialBudget - villageTotalCost;
        final Villager villagerWithHigherCost = villagers.stream().max(Villager.compareByCost).orElse(null);
        final String villagerName = String.format("%s %s", villagerWithHigherCost.getName(),villagerWithHigherCost.getSurName());
        return new ReportResponseDTO(cost, initialBudget, villageTotalCost, villagerName);
    }
}
