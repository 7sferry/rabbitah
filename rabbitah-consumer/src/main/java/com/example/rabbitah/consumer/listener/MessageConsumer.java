package com.example.rabbitah.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    @RabbitListener(queues = "${rabbitah.queue.name}")
    public void receiveMessage(String message) {
        log.warn("=== NOTIFICATION RECEIVED ===");
        log.warn("Message: {}", message);
        log.warn("=============================");
        log.info("<<< Consumed message: {}", message);
    }
}
