package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.repository.VillagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VillagerService {
    private VillagerRepository villagerRepository;

    public VillagerService(VillagerRepository villagerRepository) {
        this.villagerRepository = villagerRepository;
    }

    public List<Villager> getVillagers() {
        return villagerRepository.all();
    }

    public Optional<Villager> getById(Integer villagerId) {
        return villagerRepository.find(villagerId);
    }

    public List<Villager> filterByName(String villagerName) {
        return villagerRepository.getByName(villagerName);
    }
}
