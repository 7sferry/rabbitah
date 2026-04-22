package com.example.rabbitah.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

    @RabbitListener(queues = "${rabbitah.queue.order}")
    public void receiveMessage(String message) {
        log.warn("=== ORDER NOTIFICATION ===");
        log.warn("Message: {}", message);
        log.warn("==========================");
    }
}
