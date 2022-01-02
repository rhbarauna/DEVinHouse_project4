package com.barauna.DEVinHouse.controller;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.service.VillagerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


//TODO - Criar um método que trate o erro no controller
//TODO - Adicionar documentação nos métodos e nas rotas e criar uma pagina de documentação
//TODO - criar um README completo com as informações necessárias para subir a aplicação
//TODO - adicionar flyway https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/howto-database-initialization.html

@RestController
@RequestMapping("/villager")
public class VillagerController {

    private final VillagerService villagerService;

    public VillagerController(VillagerService villagerService) {
        this.villagerService = villagerService;
    }

    @GetMapping("/{id}")
    public VillagerDetailResponseDTO getById(@PathVariable("id") Long villagerId) throws Exception {
        return villagerService.getById(villagerId);
    }

    @GetMapping("/")
    public List<FilterVillagerResponseDTO> list() throws SQLException {
        return villagerService.getAll();
    }

    @GetMapping("/name")
    public List<FilterVillagerResponseDTO> filterByName(@RequestParam("name") String villagerName) throws SQLException {
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
    public void delete(@PathVariable("id") Long villagerId) throws Exception {
        villagerService.delete(villagerId);
    }
}
