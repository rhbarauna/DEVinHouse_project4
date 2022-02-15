package com.barauna.DEVinHouse.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Value("${amqp.queue.name}") private String queueName;
    @Value("${amqp.exchange.name}") private String exchangeName;
    @Value("${amqp.routing.key}") private String routingKey;

    @Bean
    public Queue createQueue() {
        Map<String, Object> arguments=new HashMap<>();
        arguments.put("x-message-ttl", 300);
        return new Queue(queueName, true, false, false, arguments);
    }

    @Bean
    public DirectExchange createExchange() {
//      ExchangeBuilder.directExchange(exchangeName).durable(true);
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Binding binding() {
        return new Binding(queueName, Binding.DestinationType.QUEUE,
                exchangeName, routingKey, null);
    }
}
