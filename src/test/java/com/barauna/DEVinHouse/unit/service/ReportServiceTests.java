package com.barauna.DEVinHouse.unit.service;

import com.barauna.DEVinHouse.amqp.AMQPService;
import com.barauna.DEVinHouse.dto.response.ReportResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.service.ReportService;
import com.barauna.DEVinHouse.service.UserDetailsServiceImpl;
import com.barauna.DEVinHouse.service.VillagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ReportServiceTests {

    private ReportService reportService;
    private VillagerService villagerService;
    private AMQPService amqpService;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void beforeEach() {
        villagerService = mock(VillagerService.class);
        amqpService = mock(AMQPService.class);
        userDetailsService = mock(UserDetailsServiceImpl.class);
        reportService = new ReportService( 1000F, "", "", villagerService, amqpService, userDetailsService);
    }

    @Test
    public void generateReportSuccessfully() {
        final Villager mockedVillager = new Villager("Teste", "surname", "100.000.000.00", LocalDate.now(), BigDecimal.valueOf(100F));

        when(villagerService.getTotalVillagersWage()).thenReturn(200F);
        when(villagerService.getVillagerWithHighestWage()).thenReturn(Optional.of(mockedVillager));

        ReportResponseDTO result = reportService.generate();
        verify(villagerService, atLeastOnce()).getTotalVillagersWage();
        verify(villagerService, atLeastOnce()).getVillagerWithHighestWage();
        assertNotNull(result);
        assertEquals(800, result.getCost());
        assertEquals(1000, result.getInitialBudget());
        assertEquals(200, result.getVillagersCostSum());
        assertEquals("Teste surname", result.getVillagerWithHigherCost());
    }

    @Test
    public void generateReportError() {
        when(villagerService.getTotalVillagersWage()).thenReturn(0F);
        when(villagerService.getVillagerWithHighestWage()).thenReturn(Optional.empty());

        ReportResponseDTO result = reportService.generate();
        verify(villagerService, atLeastOnce()).getTotalVillagersWage();
        verify(villagerService, atLeastOnce()).getVillagerWithHighestWage();
        assertNotNull(result);

        assertEquals(0, result.getCost());
        assertEquals(1000, result.getInitialBudget());
        assertEquals(0, result.getVillagersCostSum());
        assertEquals("No villager registered", result.getVillagerWithHigherCost());
    }
}
