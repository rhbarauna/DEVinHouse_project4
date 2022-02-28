package com.barauna.DEVinHouse.unit.amqp;

import com.barauna.DEVinHouse.amqp.AMQPService;
import com.barauna.DEVinHouse.amqp.dto.AMQPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AMQPServiceTests {

    private AMQPService amqpService;
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void beforeEach(){
        rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        this.amqpService = new AMQPService(rabbitTemplate);
    }

    @Test
    public void testSendMessage() {
        String routingKey = "";
        AMQPMessage message = mock(AMQPMessage.class);

        when(message.getBody()).thenReturn("");
        doNothing().when(rabbitTemplate).convertAndSend(routingKey, message);
        assertDoesNotThrow(()-> amqpService.sendMessage(routingKey, message));
    }

    @Test
    public void testSendMessageExpectNullPointerExceptionOnNullMessageBody() {
        String routingKey = "";
        AMQPMessage message = mock(AMQPMessage.class);

        when(message.getBody()).thenReturn(null);
        Exception ex = assertThrows(IllegalArgumentException.class, ()-> amqpService.sendMessage(routingKey, message));
        assertEquals("Message body cannot be null", ex.getMessage());
    }

    @Test
    public void testSendMessageExpectException() {
        String routingKey = "";
        AMQPMessage message = mock(AMQPMessage.class);

        when(message.getBody()).thenReturn("");
        doThrow(AmqpException.class).when(rabbitTemplate).convertAndSend(routingKey, message);
        assertThrows(Exception.class, ()-> amqpService.sendMessage(routingKey, message));
    }
}
