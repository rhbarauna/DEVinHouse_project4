package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.repository.VillagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<FilterVillagerResponseDTO> getAll() {
        List<Villager> result = villagerRepository.all();
        return result.stream().map(villager -> new FilterVillagerResponseDTO(villager.getId(), villager.getName())).collect(Collectors.toList());
    }

    public List<FilterVillagerResponseDTO> filterByName(String villagerName) {
        List<Villager> result = villagerRepository.getByName(villagerName);
        return result.stream().map(villager -> new FilterVillagerResponseDTO(villager.getId(), villager.getName())).collect(Collectors.toList());
    }

    public List<FilterVillagerResponseDTO> filterByMonth(String villagerBirthMonth) {
        List<Villager> result = villagerRepository.getByBirthMonth(villagerBirthMonth);
        return result.stream().map(villager -> new FilterVillagerResponseDTO(villager.getId(), villager.getName())).collect(Collectors.toList());
    }

    public List<FilterVillagerResponseDTO> filterByAge(Integer age) {
        List<Villager> result = villagerRepository.getByAge(age);
        return result.stream().map(villager -> new FilterVillagerResponseDTO(villager.getId(), villager.getName())).collect(Collectors.toList());
    }

    public VillagerDetailResponseDTO create(CreateVillagerRequestDTO createVillagerRequestDTO) {
        final Villager newVillager = villagerRepository.store(createVillagerRequestDTO.getName(), createVillagerRequestDTO.getSurName(), createVillagerRequestDTO.getBirthday(), createVillagerRequestDTO.getDocument(), createVillagerRequestDTO.getWage());
        return new VillagerDetailResponseDTO(newVillager.getName(), newVillager.getSurName(), newVillager.getBirthday(), newVillager.getDocument(), newVillager.getWage());
    }

    public void delete(Integer villagerId) {
        villagerRepository.delete(villagerId);
    }
}
