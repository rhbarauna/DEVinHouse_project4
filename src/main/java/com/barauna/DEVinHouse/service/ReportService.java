package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.amqp.AMQPService;
import com.barauna.DEVinHouse.amqp.dto.AMQPMessage;
import com.barauna.DEVinHouse.amqp.dto.GenerateReportMessageDTO;
import com.barauna.DEVinHouse.dto.response.ReportResponseDTO;
import com.barauna.DEVinHouse.entity.Villager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final Float villageBudget;
    private final String reportRoutingKey;
    private final VillagerService villagerService;
    private final AMQPService amqpService;
    private final String reportDlxRoutingKey;

    public ReportService(@Value("${village.budget}") Float villageBudget,
                         @Value("${amqp.routing.key.report}") String reportRoutingKey,
                         @Value("${amqp.routing.key.report.dlx}") String reportDlxRoutingKey,
                         VillagerService villagerService, AMQPService amqpService,
                         UserDetailsServiceImpl userDetailsService) {
        this.villageBudget = villageBudget;
        this.villagerService = villagerService;
        this.amqpService = amqpService;
        this.reportRoutingKey = reportRoutingKey;
        this.userDetailsService = userDetailsService;
        this.reportDlxRoutingKey = reportDlxRoutingKey;
    }

    public ReportResponseDTO generate() {
        final Float initialBudget = villageBudget;
        final Float villageTotalCost = this.villagerService.getTotalVillagersWage();
        final Float cost = initialBudget - villageTotalCost;
        final Optional<Villager> optVillagerWithHigherCost = this.villagerService.getVillagerWithHighestWage();

        if(optVillagerWithHigherCost.isEmpty()) {
            return new ReportResponseDTO(0F, initialBudget, 0F, "No villager registered");
        }

        final Villager villagerWithHigherCost = optVillagerWithHigherCost.get();
        final String villagerName = String.format("%s %s", villagerWithHigherCost.getName(),villagerWithHigherCost.getSurName());
        return new ReportResponseDTO(cost, initialBudget, villageTotalCost, villagerName);
    }

    public GenerateReportMessageDTO registerReportJob() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        GenerateReportMessageDTO dto = new GenerateReportMessageDTO();
        dto.setReportName("report_".concat(now.toString()));
        dto.setSendTo(userDetailsService.authenticated().getUsername());

        sendToReportQueue(new AMQPMessage<>(dto));
        return dto;
    }

    public void send(String routingKey, AMQPMessage<GenerateReportMessageDTO> message) {
        try {
            amqpService.sendMessage(routingKey, message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendToDLX(AMQPMessage<GenerateReportMessageDTO> message) {
        send(reportDlxRoutingKey, message);
    }

    public void sendToReportQueue(AMQPMessage<GenerateReportMessageDTO> message) {
        send(reportRoutingKey, message);
    }
}
