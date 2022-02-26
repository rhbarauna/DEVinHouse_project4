package com.barauna.DEVinHouse.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AMQPService {
    private Logger logger = LoggerFactory.getLogger(AMQPService.class);
    private RabbitTemplate rabbitTemplate;

    public AMQPService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String routingKey, Object messageBody) throws Exception {
        try{
            logger.debug("Sending message: ".concat(messageBody.toString()).concat(", to ").concat(routingKey));
            rabbitTemplate.convertAndSend(routingKey, messageBody);
        } catch (AmqpException e) {
            if(logger.isTraceEnabled()){
                logger.trace(e.getMessage(), e);
            }

            if(logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }
}
