package com.example.rabbitah.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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

    // Main fanout exchange — broadcasts to all consumer queues
    @Bean
    public FanoutExchange fanoutExchange() {
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
    public Binding orderBinding(Queue orderQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(orderQueue).to(fanoutExchange);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(emailQueue).to(fanoutExchange);
    }

    // Delay infrastructure — messages wait here until TTL expires, then forward to fanout
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange("delay-exchange");
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder.nonDurable("delay-queue")
                .deadLetterExchange(exchangeName)  // when TTL expires, forward to fanout
                .build();
    }

    @Bean
    public Binding delayBinding(Queue delayQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with("delay");
    }
}
