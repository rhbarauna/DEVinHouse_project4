package com.barauna.DEVinHouse.controller;

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
    public Optional<Villager> getById(@PathVariable("id") Integer villagerId){
        return villagerService.getById(villagerId);
    }

    @GetMapping("/")
    public List<Villager> filterByName(@RequestParam("name") String villagerName){
        return villagerService.filterByName(villagerName);
    }
}
