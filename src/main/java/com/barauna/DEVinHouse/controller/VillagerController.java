package com.barauna.DEVinHouse.controller;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.service.VillagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/villager")
public class VillagerController {

    private VillagerService villagerService;

    public VillagerController(VillagerService villagerService) {
        this.villagerService = villagerService;
    }

    @GetMapping("/{id}")
    public VillagerDetailResponseDTO getById(@PathVariable("id") Integer villagerId){
        return villagerService.getById(villagerId);
    }

    @GetMapping("/")
    public List<FilterVillagerResponseDTO> list(){
        return villagerService.getAll();
    }

    @GetMapping("/name")
    public List<FilterVillagerResponseDTO> filterByName(@RequestParam("name") String villagerName){
        return villagerService.filterByName(villagerName);
    }

    @GetMapping("/birthmonth")
    public List<FilterVillagerResponseDTO> filterByBirthMonth(@RequestParam("month") String villagerBirthMonth){
        return villagerService.filterByMonth(villagerBirthMonth);
    }

    @GetMapping("/age")
    public List<FilterVillagerResponseDTO> filterByAgeGreaterThanOrEqual(@RequestParam("age") Integer age){
        return villagerService.filterByName(villagerName);
    }

    @PostMapping("/")
    public List<FilterVillagerResponseDTO> filterByAgeGreaterThanOrEqual(@RequestBody CreateVillagerRequestDTO createVillagerRequestDTO){
        return villagerService.filterByName(villagerName);
    }

    @DeleteMapping("/{id}")
    public VillagerDetailResponseDTO deleteById(@PathVariable("id") Integer villagerId){
        return villagerService.getById(villagerId);
    }
}
