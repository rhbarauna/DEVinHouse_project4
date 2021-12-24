package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.database.repository.VillagerRepository;
import com.barauna.DEVinHouse.exception.InvalidVillagerDataException;
import com.barauna.DEVinHouse.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByName(String villagerName) {
        List<Villager> result = villagerRepository.getByName(villagerName);
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByMonth(String birthMonth) {
        List<Villager> result = villagerRepository.getByBirthMonth(birthMonth);
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByAge(Integer age) {
        List<Villager> result = villagerRepository.getByAge(age);
        return buildFilterVillagerResponseDTO(result);
    }

    public VillagerDetailResponseDTO create(CreateVillagerRequestDTO createVillagerRequestDTO) throws Exception {
        //TODO - validate requestDTO
//        validate(createVillagerRequestDTO);

        final Villager newVillager = villagerRepository.store(createVillagerRequestDTO.getName(), createVillagerRequestDTO.getSurName(), createVillagerRequestDTO.getBirthday(), createVillagerRequestDTO.getDocument(), createVillagerRequestDTO.getWage());
        return new VillagerDetailResponseDTO(newVillager.getName(), newVillager.getSurName(), newVillager.getBirthday(), newVillager.getDocument(), newVillager.getWage());
    }

    public void delete(Integer villagerId) {
        villagerRepository.delete(villagerId);
    }

    private List<FilterVillagerResponseDTO> buildFilterVillagerResponseDTO(List<Villager> result) {
        return result.stream().map(villager -> new FilterVillagerResponseDTO(villager.getId(), villager.getName())).collect(Collectors.toList());
    }

    private void validate(CreateVillagerRequestDTO createVillagerRequestDTO) throws Exception {
        if(!StringUtils.validateCPF(createVillagerRequestDTO.getDocument())) {
            throw new InvalidVillagerDataException("Invalid CPF value.");
        }

        if(!StringUtils.validateName(createVillagerRequestDTO.getName()) || !createVillagerRequestDTO.getName().trim().isEmpty()) {
            throw new InvalidVillagerDataException("Invalid name value. Cannot be only spaces nor contain number.");
        }

        if(!StringUtils.validateName(createVillagerRequestDTO.getSurName()) || !createVillagerRequestDTO.getSurName().trim().isEmpty()) {
            throw new InvalidVillagerDataException("Invalid surname value. Cannot be only spaces nor contain number.");
        }

        if(createVillagerRequestDTO.getWage() < 0) {
            throw new InvalidVillagerDataException("Wage cannot be negative.");
        }

        if(LocalDate.now().isBefore(createVillagerRequestDTO.getBirthday())){
            throw new InvalidVillagerDataException("Birthdate cannot be bigger than today.");
        }
    }
}
