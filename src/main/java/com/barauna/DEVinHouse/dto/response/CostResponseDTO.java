package com.barauna.DEVinHouse.dto.response;

import com.barauna.DEVinHouse.entity.Villager;

import java.util.Optional;

public class CostResponseDTO {
    private Float cost;
    private Float initialBudget;
    private Float villageTotalCost;
    private Optional<Villager> villagerWithHigherCost;

    public CostResponseDTO(Float cost, Float initialBudget, Float villageTotalCost, Optional<Villager> villagerWithHigherCost) {
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
