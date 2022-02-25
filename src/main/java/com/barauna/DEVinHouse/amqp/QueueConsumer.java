package com.barauna.DEVinHouse.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {
    @RabbitListener(queues="${amqp.queue.name}")
    public void consume(Object message) {
        System.out.println(message);
    }
}
