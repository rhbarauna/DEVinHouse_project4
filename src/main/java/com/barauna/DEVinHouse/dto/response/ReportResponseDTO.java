package com.barauna.DEVinHouse.dto.response;

public class ReportResponseDTO {
    private final Float cost;
    private final Float initialBudget;
    private final Float villagersCostSum;
    private final String villagerWithHigherCost;

    public ReportResponseDTO(Float cost, Float initialBudget, Float villageTotalCost, String villagerWithHigherCost) {
        this.cost = cost;
        this.initialBudget = initialBudget;
        this.villagersCostSum = villageTotalCost;
        this.villagerWithHigherCost = villagerWithHigherCost;
    }

    public Float getCost() {
        return cost;
    }

    public Float getInitialBudget() {
        return initialBudget;
    }

    public Float getVillagersCostSum() {
        return villagersCostSum;
    }

    public String getVillagerWithHigherCost() {
        return villagerWithHigherCost;
    }

    @Override
    public String toString() {
        return "ReportResponseDTO {" +
            "'cost':" + cost +
            ", 'initialBudget':" + initialBudget +
            ", 'villagersCostSum':" + villagersCostSum +
            ", 'villagerWithHigherCost':'" + villagerWithHigherCost + '\'' +
        "}";
    }
}
