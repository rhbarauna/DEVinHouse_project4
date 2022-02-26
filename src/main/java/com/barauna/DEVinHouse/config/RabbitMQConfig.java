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


    @Value("${amqp.queue.report}") private String reportQueue;
    @Value("${amqp.exchange.report}") private String reportExchange;
    @Value("${amqp.routing.key.report}") private String reportRoutingKey;

    @Bean(name="report-queue")
    public Queue createReportQueue() {
        Map<String, Object> arguments=new HashMap<>();
        arguments.put("x-message-ttl", 300);
        return new Queue(reportQueue, true, false, false, arguments);
    }

    @Bean(name="report-exchange")
    public DirectExchange createReportExchange() {
//      ExchangeBuilder.directExchange(exchangeName).durable(true);
        return new DirectExchange(reportExchange, true, false);
    }

    @Bean(name="report-binding")
    public Binding reportQueueBinding() {
        return new Binding(reportQueue, Binding.DestinationType.QUEUE,
                reportExchange, reportRoutingKey, null);
    }
}
