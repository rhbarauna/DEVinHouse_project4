package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.database.repository.VillagerRepository;
import com.barauna.DEVinHouse.exception.InvalidVillagerDataException;
import com.barauna.DEVinHouse.to.UserTO;
import com.barauna.DEVinHouse.utils.VillagerUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VillagerService {
    private final VillagerRepository villagerRepository;
    private final UserService userService;

    public VillagerService(VillagerRepository villagerRepository, UserService userService) {
        this.villagerRepository = villagerRepository;
        this.userService = userService;
    }

    public List<Villager> getVillagers() throws SQLException {
        return villagerRepository.all();
    }

    public VillagerDetailResponseDTO getById(Long villagerId) throws Exception {
        Optional<Villager> result = villagerRepository.find(villagerId);

        if(result.isEmpty()) {
            return null;
        }

        Villager villager = result.get();
        UserTO userTO = userService.getByVillagerId(villagerId);

        return new VillagerDetailResponseDTO(
                villager.getName(), villager.getSurName(),
                villager.getBirthday(), villager.getDocument(), villager.getWage(),
                userTO.getEmail(), new ArrayList<>(userTO.getRoles()));
    }

    public List<FilterVillagerResponseDTO> getAll() throws SQLException {
        List<Villager> result = villagerRepository.all();
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByName(String villagerName) throws SQLException {
        List<Villager> result = villagerRepository.getByName(villagerName);
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByMonth(String birthMonth) throws SQLException {
        List<Villager> result = villagerRepository.getByBirthMonth(birthMonth);
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByAge(Integer age) throws SQLException {
        List<Villager> result = villagerRepository.getByAge(age);
        return buildFilterVillagerResponseDTO(result);
    }

    public VillagerDetailResponseDTO create(CreateVillagerRequestDTO createVillagerRequestDTO) throws Exception {
        validate(createVillagerRequestDTO);

        final Villager newVillager = villagerRepository.store(
            createVillagerRequestDTO.getName(),
            createVillagerRequestDTO.getSurName(),
            createVillagerRequestDTO.getBirthday(),
            createVillagerRequestDTO.getDocument(),
            createVillagerRequestDTO.getWage()
        );

        try {
            UserTO userTO = userService.create(newVillager.getId(), createVillagerRequestDTO.getEmail(), createVillagerRequestDTO.getPassword(), new HashSet<>(createVillagerRequestDTO.getRoles()));
            return new VillagerDetailResponseDTO(newVillager.getName(), newVillager.getSurName(), newVillager.getBirthday(), newVillager.getDocument(), newVillager.getWage(), userTO.getEmail(), new ArrayList<String>(userTO.getRoles()));
        } catch(Exception e) {
            this.delete(newVillager.getId());
            throw e;
        }
    }

    public void delete(Long villagerId) throws Exception {
        userService.deleteByVillagerId(villagerId);
        villagerRepository.delete(villagerId);
    }

    private List<FilterVillagerResponseDTO> buildFilterVillagerResponseDTO(List<Villager> result) {
        return result.stream().map(villager -> new FilterVillagerResponseDTO(villager.getId(), villager.getName())).collect(Collectors.toList());
    }

    private void validate(CreateVillagerRequestDTO createVillagerRequestDTO) throws Exception {
        if(!VillagerUtils.isValidCPF(createVillagerRequestDTO.getDocument())) {
            throw new InvalidVillagerDataException("Invalid CPF.");
        }

        if(!VillagerUtils.isValidName(createVillagerRequestDTO.getName())) {
            throw new InvalidVillagerDataException("Invalid name. Cannot be only spaces nor contain number.");
        }

        if(!VillagerUtils.isValidName(createVillagerRequestDTO.getSurName())) {
            throw new InvalidVillagerDataException("Invalid surname. Cannot be only spaces nor contain number.");
        }

        if(createVillagerRequestDTO.getWage() < 0) {
            throw new InvalidVillagerDataException("Wage cannot be negative.");
        }

        if(LocalDate.now().isBefore(createVillagerRequestDTO.getBirthday())){
            throw new InvalidVillagerDataException("Birthdate cannot be bigger than today.");
        }
    }
}
