package com.barauna.DEVinHouse.amqp.dto;


public class GenerateReportMessageDTO {
    private String reportName;

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }
}
