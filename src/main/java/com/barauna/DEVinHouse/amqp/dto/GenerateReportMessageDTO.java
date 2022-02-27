package com.barauna.DEVinHouse.amqp.dto;


public class GenerateReportMessageDTO {
    private String reportName;
    private String sendToEmail;

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setSendTo(String sendToEmail) {
        this.sendToEmail = sendToEmail;
    }

    public String getReportName() {
        return reportName;
    }

    public String getSendTo() {
        return this.sendToEmail;
    }
}
