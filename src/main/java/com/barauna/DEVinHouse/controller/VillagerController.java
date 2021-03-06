package com.barauna.DEVinHouse.controller;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.service.VillagerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/villager")
public class VillagerController {

    private final VillagerService villagerService;

    public VillagerController(VillagerService villagerService) {
        this.villagerService = villagerService;
    }

    @GetMapping("/{id}")
    public VillagerDetailResponseDTO getById(@PathVariable("id") Long villagerId) {
        Optional<VillagerDetailResponseDTO> response = villagerService.getById(villagerId);
        return response.get();
    }

    @GetMapping("/")
    public List<FilterVillagerResponseDTO> list() {
        return villagerService.getAll();
    }

    @GetMapping("/name")
    public List<FilterVillagerResponseDTO> filterByName(@RequestParam("name") String villagerName) {
        return villagerService.filterByName(villagerName);
    }

    @GetMapping("/birth")
    public List<FilterVillagerResponseDTO> filterByBirthMonth(@RequestParam("month") String birthMonth) throws SQLException {
        return villagerService.filterByMonth(birthMonth);
    }

    @GetMapping("/age/{age}")
    public List<FilterVillagerResponseDTO> filterByAgeGreaterThanOrEqual(@PathVariable("age") Integer age) throws SQLException {
        return villagerService.filterByAge(age);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public VillagerDetailResponseDTO store(@RequestBody CreateVillagerRequestDTO createVillagerRequestDTO) throws Exception {
        return villagerService.create(createVillagerRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long villagerId) {
        villagerService.delete(villagerId);
    }
}
