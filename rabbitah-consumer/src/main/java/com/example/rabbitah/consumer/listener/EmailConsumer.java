package com.example.rabbitah.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

    @RabbitListener(queues = "${rabbitah.queue.email}")
    public void receiveMessage(String message) {
        log.warn("=== EMAIL NOTIFICATION ===");
        log.warn("Message: {}", message);
        log.warn("==========================");
    }
}
