package com.barauna.DEVinHouse.controller;

import com.barauna.DEVinHouse.dto.response.ReportResponseDTO;
import com.barauna.DEVinHouse.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/generate")
    public ReportResponseDTO generate() throws SQLException {
        return reportService.generate();
    }
}
