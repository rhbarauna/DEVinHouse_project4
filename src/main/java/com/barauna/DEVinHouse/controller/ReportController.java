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
        GenerateReportMessageDTO dto = new GenerateReportMessageDTO();
        boolean response = false;
        for(int i = 0; i < 1000; i++) {
            LocalDateTime now = LocalDateTime.now();
            dto.setReportName("report_".concat(now.toString()));
            logger.info("Generating report ".concat(String.valueOf(i)).concat(" ").concat(dto.getReportName()));
            response = reportService.registerReportJob(dto);
        }

        if(!response) {
            return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .build();
        }

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(dto.getReportName());
    }
}
