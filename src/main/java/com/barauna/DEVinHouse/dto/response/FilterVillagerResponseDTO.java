package com.barauna.DEVinHouse.dto.response;

public class FilterVillagerResponseDTO {
    private Long id;
    private String name;

    public FilterVillagerResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
