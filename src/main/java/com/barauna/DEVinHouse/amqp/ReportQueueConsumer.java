package com.barauna.DEVinHouse.amqp;

import com.barauna.DEVinHouse.amqp.dto.AMQPMessage;
import com.barauna.DEVinHouse.amqp.dto.GenerateReportMessageDTO;
import com.barauna.DEVinHouse.dto.response.ReportResponseDTO;
import com.barauna.DEVinHouse.service.EmailService;
import com.barauna.DEVinHouse.service.ReportService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.istack.ByteArrayDataSource;
import javax.activation.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ReportQueueConsumer {

    private final Logger logger = LoggerFactory.getLogger(ReportQueueConsumer.class);
    private final ReportService reportService;
    private final EmailService emailService;
    private final Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

    public ReportQueueConsumer(ReportService reportService, EmailService emailService) {
        this.reportService = reportService;
        this.emailService = emailService;
    }

    @RabbitListener(queues="${amqp.queue.report}")
    public void consume(AMQPMessage<GenerateReportMessageDTO> message) {
        try {
            logger.info("Starting report ".concat(message.getBody().getReportName()).concat(" generation."));
            final ReportResponseDTO generated = reportService.generate();
            writePdf(message.getBody().getReportName(), generated);
            logger.info("Report generated. ".concat(generated.toString()));
        } catch(Exception e) {
            reportService.sendToDLX(message);
        }
    }

    @RabbitListener(queues="${amqp.queue.report.dlx}")
    private void consumeDLXQueue(AMQPMessage<GenerateReportMessageDTO> message) {
        message.incrementTries();
        if ( message.getTries() < 3 ) {
            reportService.sendToReportQueue(message);
            return;
        }

        try {
            emailService.send(message.getBody().getSendTo(), "Falha ao gerar relatório", "Desculpe. Não conseguimos gerar o relatório solicitado. Tente novamente.");
        } catch(Exception e) {
            logger.error("Send report email error: " + e.getMessage());
        }
    }

    private void writePdf(String fileName, ReportResponseDTO content) throws IOException, DocumentException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName.concat(".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            logger.info("Writing report lines");

            addParagraph(document, "Villager with higher cost: ".concat(content.getVillagerWithHigherCost()));
            addParagraph(document, "Total village cost: ".concat(content.getVillagersCostSum().toString()));
            addParagraph(document, "Villager initial budget: ".concat(content.getInitialBudget().toString()));
            addParagraph(document, "Villager final balance: ".concat(content.getCost().toString()));

            document.close();
        } catch(Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            if(null != outputStream) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch(Exception ex) {
                    logger.error(ex.getMessage());
                    throw ex;
                }
            }
        }
    }

    private void addParagraph(Document document, String content) throws DocumentException {
        final Paragraph paragraph = new Paragraph(content,font);
        document.add(paragraph);
    }
}
