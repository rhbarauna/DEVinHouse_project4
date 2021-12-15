package com.barauna.DEVinHouse.controller;

import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.service.VillageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/village")
public class VillageController {

    private VillageService villageService;

    public VillageController(VillageService villageService) {
        this.villageService = villageService;
    }

    @GetMapping("/")
    public String hellowWorld() {
        return "Hello World";
    }

    @GetMapping("/villagers")
    public List<Villager> index() {
        return villageService.getVillagers();
    }

    @GetMapping("/total-cost")
    public TotalCostResponseDTO totalCost() {
        final Float cost = villageService.getTotalCost();
        final Float initialBudget = villageService.getInitialBudget();
        final Float villageTotalCost = villageService.getVillageTotalCost();
        final Optional<Villager> biggestVillagerCost = villageService.getBiggestVillagerCost();

        return new TotalCostResponseDTO(cost, initialBudget, villageTotalCost, biggestVillagerCost);
    }


    private class TotalCostResponseDTO {
        private Float cost;
        private Float initialBudget;
        private Float villageTotalCost;
        private Optional<Villager> villagerWithHigherCost;

        public TotalCostResponseDTO(Float cost, Float initialBudget, Float villageTotalCost, Optional<Villager> villagerWithHigherCost) {
            this.cost = cost;
            this.initialBudget = initialBudget;
            this.villageTotalCost = villageTotalCost;
            this.villagerWithHigherCost = villagerWithHigherCost;
        }

        public Float getCost() {
            return cost;
        }

        public void setCost(Float cost) {
            this.cost = cost;
        }

        public Float getInitialBudget() {
            return initialBudget;
        }

        public void setInitialBudget(Float initialBudget) {
            this.initialBudget = initialBudget;
        }

        public Float getVillageTotalCost() {
            return villageTotalCost;
        }

        public void setVillageTotalCost(Float villageTotalCost) {
            this.villageTotalCost = villageTotalCost;
        }

        public Optional<Villager> getvillagerWithHigherCost() {
            return villagerWithHigherCost;
        }

        public void setvillagerWithHigherCost(Optional<Villager> villagerWithHigherCost) {
            this.villagerWithHigherCost = villagerWithHigherCost;
        }
    }
}
