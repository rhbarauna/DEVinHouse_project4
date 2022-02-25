package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.dto.response.ReportResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private Float villageBudget;
    private VillagerService villagerService;

    public ReportService(@Value("${village.budget}") Float villageBudget, VillagerService villagerService) {
        this.villageBudget = villageBudget;
        this.villagerService = villagerService;
    }

    public ReportResponseDTO generate() {
        final Float initialBudget = villageBudget;
        final Float villageTotalCost = this.villagerService.getTotalVillagersWage();
        final Float cost = initialBudget - villageTotalCost;
        final Optional<Villager> optVillagerWithHigherCost = this.villagerService.getVillagerWithHighestWage();

        if(optVillagerWithHigherCost.isEmpty()) {
            return new ReportResponseDTO(0F, initialBudget, 0F, "No villager registered");
        }

        final Villager villagerWithHigherCost = optVillagerWithHigherCost.get();
        final String villagerName = String.format("%s %s", villagerWithHigherCost.getName(),villagerWithHigherCost.getSurName());
        return new ReportResponseDTO(cost, initialBudget, villageTotalCost, villagerName);
    }
}
