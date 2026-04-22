package com.example.rabbitah.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public void sendScheduledMessage(String message, LocalDateTime scheduledAt) {
        long delayMs = Duration.between(LocalDateTime.now(), scheduledAt).toMillis();
        if (delayMs < 0) {
            delayMs = 0;
        }

        long finalDelayMs = delayMs;
        rabbitTemplate.convertAndSend("delay-exchange", "delay", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(finalDelayMs));
            return msg;
        });
        log.info(">>> Scheduled message in {}ms (at {}): {}", delayMs, scheduledAt, message);
    }
}
