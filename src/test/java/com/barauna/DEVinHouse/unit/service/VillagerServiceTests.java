package com.barauna.DEVinHouse.unit.service;

import com.barauna.DEVinHouse.dto.request.CreateVillagerRequestDTO;
import com.barauna.DEVinHouse.dto.response.FilterVillagerResponseDTO;
import com.barauna.DEVinHouse.dto.response.VillagerDetailResponseDTO;
import com.barauna.DEVinHouse.entity.Role;
import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.repository.VillagerRepository;
import com.barauna.DEVinHouse.service.UserService;
import com.barauna.DEVinHouse.service.VillagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VillagerServiceTests {

    private VillagerService villagerService;
    private VillagerRepository villagerRepository;
    private UserService userService;
    private final List<Villager> listOfVillagers = List.of(
            new Villager("josé", "surname 1", "100.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L)),
            new Villager("luiz", "surname 2", "200.000.000-00", LocalDate.now(), BigDecimal.valueOf(20L)),
            new Villager("maria", "surname 3", "300.000.000-00", LocalDate.now(), BigDecimal.valueOf(30L))
    );

    @BeforeEach
    public void beforeEach(){
        this.villagerRepository = mock(VillagerRepository.class);
        this.userService = mock(UserService.class);
        this.villagerService = new VillagerService(villagerRepository, userService);
    }

    @Test
    public void testGetVillagers() {
        when(villagerRepository.findAll()).thenReturn(listOfVillagers);

        List<Villager> serviceResponse = villagerService.getVillagers();
        assertEquals(listOfVillagers, serviceResponse);
    }

    @Test
    public void testEmptyResponseOnGetVillagers() {
        final List<Villager> villagersResponse = List.of();
        when(villagerRepository.findAll()).thenReturn(villagersResponse);
        List<Villager> serviceResponse = villagerService.getVillagers();
        assertTrue(serviceResponse.isEmpty());
    }

    @Test
    public void getVillagerDataByIdSuccessfully() {
        Long villagerId = 1L;
        Villager testVillager = new Villager("Villager", "surname 1", "100.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L));
        Set<Role> testRoles = Set.of(new Role(1L,"Admin", "Desc"));
        User testUser = new User(1L, "villager@test.com", "123456", testVillager, testRoles);

        testVillager.setUser(testUser);

        when(villagerRepository.findById(villagerId)).thenReturn(Optional.of(testVillager));

        VillagerDetailResponseDTO response = villagerService.getById(villagerId).get();

        assertEquals(testVillager.getName(), response.getName());
        assertEquals(testVillager.getSurName(), response.getSurName());
        assertEquals(testVillager.getBirthday(), response.getBirthday());
        assertEquals(testVillager.getDocument(), response.getDocument());
        assertEquals(testVillager.getWage(), response.getWage());
        assertEquals(testUser.getEmail(), response.getEmail());
        assertFalse(response.getRoles().isEmpty());
    }

    @Test
    public void testEmptyResponseWhenVillagerNotFound() {
        when(villagerRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<VillagerDetailResponseDTO> response = villagerService.getById(1L);
        assertInstanceOf(Optional.class, response);
        assertTrue(response.isEmpty());
    }

    @Test
    public void getListOfAllVillagersAsFilterVillagerResponseDTO(){
        when(villagerRepository.findAll()).thenReturn(listOfVillagers);
        final List<FilterVillagerResponseDTO> filterVillagerResponseDTO = this.villagerService.getAll();
        assertFalse(filterVillagerResponseDTO.isEmpty());
        assertEquals(filterVillagerResponseDTO.size(),listOfVillagers.size());
    }

    @Test
    public void getAllReturningEmptyList(){
        final List<Villager> villagersResponse = List.of();
        when(villagerRepository.findAll()).thenReturn(villagersResponse);
        final List<FilterVillagerResponseDTO> filterVillagerResponseDTO = this.villagerService.getAll();
        assertTrue(filterVillagerResponseDTO.isEmpty());
    }

    @Test
    public void filterVillagersByName(){
        final String villagerName = "josé";
        final List<Villager> villagers = List.of(
                new Villager("josé", "surname 1", "100.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L)),
                new Villager("josé", "vieira", "200.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L))
        );
        when(villagerRepository.findByNameContaining(villagerName)).thenReturn(villagers);
        final List<FilterVillagerResponseDTO> filterVillagerResponseDTO = this.villagerService.filterByName(villagerName);

        assertFalse(filterVillagerResponseDTO.isEmpty());
        assertEquals(filterVillagerResponseDTO.size(), villagers.size());
    }

    @Test
    public void filterVillagersByAge() throws SQLException {
        final List<Villager> villagers = List.of(
                new Villager("josé", "surname 1", "100.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L)),
                new Villager("josé", "vieira", "200.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L))
        );
        when(villagerRepository.findByAgeGreaterThanOrEqualTo(10)).thenReturn(villagers);
        final List<FilterVillagerResponseDTO> filterVillagerResponseDTO = this.villagerService.filterByAge(10);

        assertFalse(filterVillagerResponseDTO.isEmpty());
        assertEquals(filterVillagerResponseDTO.size(), villagers.size());
    }

    @Test
    public void filterVillagersByBirthMonth() throws SQLException {
        final List<Villager> villagers = List.of(
                new Villager("josé", "surname 1", "100.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L)),
                new Villager("josé", "vieira", "200.000.000-00", LocalDate.now(), BigDecimal.valueOf(10L))
        );
        when(villagerRepository.findByBirthMonth("January")).thenReturn(villagers);
        final List<FilterVillagerResponseDTO> filterVillagerResponseDTO = this.villagerService.filterByMonth("January");

        assertFalse(filterVillagerResponseDTO.isEmpty());
        assertEquals(filterVillagerResponseDTO.size(), villagers.size());
    }

    @Test
    public void createVillagerSuccessfully() throws Exception {
        Set<String> roles = Set.of("ADMIN");
        CreateVillagerRequestDTO createVillagerRequestDTO = new CreateVillagerRequestDTO();
        createVillagerRequestDTO.setName("José");
        createVillagerRequestDTO.setSurName("Severino");
        createVillagerRequestDTO.setBirthday(LocalDate.now());
        createVillagerRequestDTO.setDocument("100.000.000-00");
        createVillagerRequestDTO.setEmail("jose.severino@test.com");
        createVillagerRequestDTO.setPassword("123456");
        createVillagerRequestDTO.setWage(BigDecimal.valueOf(100.00));
        createVillagerRequestDTO.setRoles(List.copyOf(roles));

        final VillagerDetailResponseDTO villagerDetailResponseDTO = villagerService.create(createVillagerRequestDTO);
        assertNotNull(villagerDetailResponseDTO);
        assertEquals(createVillagerRequestDTO.getName(), villagerDetailResponseDTO.getName());
        assertEquals(createVillagerRequestDTO.getSurName(), villagerDetailResponseDTO.getSurName());
        assertEquals(createVillagerRequestDTO.getBirthday(), villagerDetailResponseDTO.getBirthday());
        assertEquals(createVillagerRequestDTO.getDocument(), villagerDetailResponseDTO.getDocument());
        assertEquals(createVillagerRequestDTO.getEmail(), villagerDetailResponseDTO.getEmail());
        assertEquals(createVillagerRequestDTO.getWage(), villagerDetailResponseDTO.getWage());
        assertEquals(createVillagerRequestDTO.getRoles(), villagerDetailResponseDTO.getRoles());
    }

    @Test
    public void createVillagerThrowsErrorOnInvalidCPF() {
        CreateVillagerRequestDTO createVillagerRequestDTO = new CreateVillagerRequestDTO();
        final Exception exception1 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception1.getMessage(), "Invalid CPF.");

        createVillagerRequestDTO.setDocument("");
        final Exception exception2 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception2.getMessage(), "Invalid CPF.");

        createVillagerRequestDTO.setDocument("100.00.0000-00");
        final Exception exception3 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception3.getMessage(), "Invalid CPF.");

        createVillagerRequestDTO.setDocument("100.c00.000-00");
        final Exception exception4 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception4.getMessage(), "Invalid CPF.");
    }
    @Test
    public void createVillagerThrowsErrorOnInvalidName() {
        CreateVillagerRequestDTO createVillagerRequestDTO = new CreateVillagerRequestDTO();
        createVillagerRequestDTO.setDocument("100.000.000-00");

        final Exception exception1 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception1.getMessage(), "Invalid name. Cannot be only spaces nor contain number.");

        createVillagerRequestDTO.setName("");
        final Exception exception2 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception2.getMessage(), "Invalid name. Cannot be only spaces nor contain number.");

        createVillagerRequestDTO.setName("1ndio");
        final Exception exception3 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception3.getMessage(), "Invalid name. Cannot be only spaces nor contain number.");

        createVillagerRequestDTO.setName("  Índio");
        final Exception exception4 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception4.getMessage(), "Invalid name. Cannot be only spaces nor contain number.");
    }
    @Test
    public void createVillagerThrowsErrorOnInvalidSurName() {
        CreateVillagerRequestDTO createVillagerRequestDTO = new CreateVillagerRequestDTO();
        createVillagerRequestDTO.setName("José");
        createVillagerRequestDTO.setDocument("100.000.000-00");

        final Exception exception1 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception1.getMessage(), "Invalid surname. Cannot be only spaces nor contain number.");

        createVillagerRequestDTO.setSurName("");
        final Exception exception2 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception2.getMessage(), "Invalid surname. Cannot be only spaces nor contain number.");

        createVillagerRequestDTO.setSurName("1ndio");
        final Exception exception3 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception3.getMessage(), "Invalid surname. Cannot be only spaces nor contain number.");

        createVillagerRequestDTO.setSurName("  Índio");
        final Exception exception4 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception4.getMessage(), "Invalid surname. Cannot be only spaces nor contain number.");
    }

    @Test
    public void createVillagerThrowsErrorOnInvalidWage() {
        CreateVillagerRequestDTO createVillagerRequestDTO = new CreateVillagerRequestDTO();
        createVillagerRequestDTO.setName("José");
        createVillagerRequestDTO.setSurName("da Silva");
        createVillagerRequestDTO.setDocument("100.000.000-00");

        final Exception exception1 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception1.getMessage(), "Wage cannot be null.");

        createVillagerRequestDTO.setWage(BigDecimal.valueOf(-1L));
        final Exception exception2 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception2.getMessage(), "Wage cannot be negative.");
    }

    @Test
    public void createVillagerThrowsErrorOnInvalidBirthdate() {
        CreateVillagerRequestDTO createVillagerRequestDTO = new CreateVillagerRequestDTO();
        createVillagerRequestDTO.setName("José");
        createVillagerRequestDTO.setSurName("da Silva");
        createVillagerRequestDTO.setDocument("100.000.000-00");
        createVillagerRequestDTO.setWage(BigDecimal.TEN);

        final Exception exception1 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception1.getMessage(), "Birthdate cannot be null.");

        createVillagerRequestDTO.setBirthday(LocalDate.now().plus(Period.ofDays(2)));
        final Exception exception2 = assertThrows(Exception.class, () -> villagerService.create(createVillagerRequestDTO));
        assertEquals(exception2.getMessage(), "Birthdate cannot be bigger than today.");
    }

    @Test
    public void testDeleteVillagerById() {
        assertDoesNotThrow(()->villagerService.delete(1L));
    }

    @Test
    public void testGetTotalVillagersWage() {
        when(villagerRepository.findAll()).thenReturn(listOfVillagers);
        Float totalVillagersWage = villagerService.getTotalVillagersWage();
        assertEquals(60, totalVillagersWage);
    }
    @Test
    public void testGetVillagerWithHighestWage() {
        when(villagerRepository.findAll()).thenReturn(listOfVillagers);
        final Optional<Villager> villagerWithHighestWage = villagerService.getVillagerWithHighestWage();

        assertFalse(villagerWithHighestWage.isEmpty());
        assertEquals(listOfVillagers.get(2), villagerWithHighestWage.get());
    }
}
