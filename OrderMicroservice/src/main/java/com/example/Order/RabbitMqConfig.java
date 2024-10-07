package com.example.Order;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private static final String queueName = "sample_queue";
    @Bean
    public Queue sampleQueue(){
        return new Queue(queueName);
    }
}
