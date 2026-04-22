package com.example.rabbitah.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitah.exchange.name}")
    private String exchangeName;

    @Value("${rabbitah.queue.order}")
    private String orderQueueName;

    @Value("${rabbitah.queue.email}")
    private String emailQueueName;

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueueName, false);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueueName, false);
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(orderQueue).to(exchange);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange);
    }
}
