package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
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

    public VillagerDetailResponseDTO getById(Integer villagerId) {
        Optional<Villager> result = villagerRepository.find(villagerId);

        if(result.isEmpty()) {
            return null;
        }

        Villager villager = result.get();

        return new VillagerDetailResponseDTO(villager.getName(), villager.getSurName(), villager.getBirthday(), villager.getDocument(), villager.getWage());
    }

    public List<Villager> filterByName(String villagerName) {
        return villagerRepository.getByName(villagerName);
    }
}
