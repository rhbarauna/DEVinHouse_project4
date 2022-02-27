package com.barauna.DEVinHouse.controller;

import com.barauna.DEVinHouse.amqp.dto.GenerateReportMessageDTO;
import com.barauna.DEVinHouse.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/report")
public class ReportController {

    private Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/generate")
    public ResponseEntity<String> generate() {
        try{
            final GenerateReportMessageDTO generateReportMessageDTO = reportService.registerReportJob();
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generateReportMessageDTO.getReportName().concat(" will be generated and sent to your email."));

        } catch(Exception e) {
            return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(e.getMessage());
        }
    }
}
