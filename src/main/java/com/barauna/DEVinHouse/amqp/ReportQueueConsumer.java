package com.barauna.DEVinHouse.amqp;

import com.barauna.DEVinHouse.amqp.dto.GenerateReportMessageDTO;
import com.barauna.DEVinHouse.dto.response.ReportResponseDTO;
import com.barauna.DEVinHouse.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ReportQueueConsumer {

    private Logger logger = LoggerFactory.getLogger(ReportQueueConsumer.class);
    private ReportService reportService;

    public ReportQueueConsumer(ReportService reportService) {
        this.reportService = reportService;
    }

    @RabbitListener(queues="${amqp.queue.report}")
    public void consume(GenerateReportMessageDTO message) {
            logger.info("Starting report ".concat(message.getReportName()).concat(" generation."));
            final ReportResponseDTO generated = reportService.generate();
            logger.info("Report generated.".concat("").concat(generated.toString()));

    }
}
