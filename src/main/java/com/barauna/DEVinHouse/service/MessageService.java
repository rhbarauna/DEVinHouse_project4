package com.barauna.DEVinHouse.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MessageService {

    private RabbitTemplate rabbitTemplate;

    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //TODO - receber o objeto e envelopá-lo
    public Object createMessage(Object dto) {
        Object response = new Object();
        rabbitTemplate.convertAndSend("direct.pix", "devin.pix", dto);

        return response;
    }
}
