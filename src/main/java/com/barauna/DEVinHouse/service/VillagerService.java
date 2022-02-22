package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.entity.Role;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.repository.VillagerRepository;
import com.barauna.DEVinHouse.exception.InvalidVillagerDataException;
import com.barauna.DEVinHouse.utils.VillagerUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VillagerService {
    private final VillagerRepository villagerRepository;
    private final UserService userService;

    public VillagerService(VillagerRepository villagerRepository, UserService userService) {
        this.villagerRepository = villagerRepository;
        this.userService = userService;
    }

    public List<Villager> getVillagers() {
        final Iterable<Villager> all = this.villagerRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
    }

    public Optional<VillagerDetailResponseDTO> getById(Long villagerId) {
        Optional<Villager> result = villagerRepository.findById(villagerId);

        if(result.isEmpty()) {
            return Optional.empty();
        }

        Villager villager = result.get();

        return Optional.of(
            new VillagerDetailResponseDTO(
                villager.getName(), villager.getSurName(),
                villager.getBirthday(), villager.getDocument(), villager.getWage(),
                villager.getUser().getEmail(), new ArrayList(villager.getUser().getRoles()))
        );
    }

    public List<FilterVillagerResponseDTO> getAll() {
        List<Villager> result = this.getVillagers();
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByName(String villagerName) {
        List<Villager> result = villagerRepository.findByNameContaining(villagerName);
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByMonth(String birthMonth) throws SQLException {
        List<Villager> result = villagerRepository.findByBirthMonth(birthMonth);
        return buildFilterVillagerResponseDTO(result);
    }

    public List<FilterVillagerResponseDTO> filterByAge(Integer age) throws SQLException {
        List<Villager> result = villagerRepository.findByAgeGreaterThanOrEqualTo(age);
        return buildFilterVillagerResponseDTO(result);
    }

    @Transactional
    public VillagerDetailResponseDTO create(CreateVillagerRequestDTO createVillagerRequestDTO) throws Exception {
        validate(createVillagerRequestDTO);
        final Villager newVillager = new Villager(
                createVillagerRequestDTO.getName(),
                createVillagerRequestDTO.getSurName(),
                createVillagerRequestDTO.getDocument(),
                createVillagerRequestDTO.getBirthday(),
                createVillagerRequestDTO.getWage()
        );

        userService.create(newVillager, createVillagerRequestDTO.getEmail(), createVillagerRequestDTO.getPassword(), new HashSet<>(createVillagerRequestDTO.getRoles()));
        villagerRepository.save(newVillager);

        return new VillagerDetailResponseDTO(newVillager.getName(), newVillager.getSurName(), newVillager.getBirthday(), newVillager.getDocument(), newVillager.getWage(), createVillagerRequestDTO.getEmail(), createVillagerRequestDTO.getRoles());
    }

    @Transactional
    public void delete(Long villagerId) {
        villagerRepository.deleteById(villagerId);
    }

    public Float getTotalVillagersWage() {
        return this.getVillagers().stream().reduce(
                0F,(accumulator, villager) -> accumulator + villager.getWage().floatValue(),
                Float::sum
        );
    }


    public Optional<Villager> getVillagerWithHighestWage() {
        return this.getVillagers().stream().max(Villager.compareByCost);
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

        if(createVillagerRequestDTO.getWage().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidVillagerDataException("Wage cannot be negative.");
        }

        if(LocalDate.now().isBefore(createVillagerRequestDTO.getBirthday())){
            throw new InvalidVillagerDataException("Birthdate cannot be bigger than today.");
        }
    }
}
