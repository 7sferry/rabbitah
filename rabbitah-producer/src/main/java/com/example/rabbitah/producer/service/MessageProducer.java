package com.example.rabbitah.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitah.exchange.name}")
    private String exchangeName;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchangeName, "", message);
        log.info(">>> Sent message to ALL consumers: {}", message);
    }
}
