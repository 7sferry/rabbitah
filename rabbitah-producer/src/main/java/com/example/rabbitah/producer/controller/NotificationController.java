package com.example.rabbitah.producer.controller;

import com.example.rabbitah.producer.service.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final MessageProducer messageProducer;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message, RedirectAttributes redirectAttributes) {
        messageProducer.sendMessage(message);
        redirectAttributes.addFlashAttribute("success", "Message broadcast: " + message);
        return "redirect:/";
    }

    @PostMapping("/schedule")
    public String scheduleMessage(@RequestParam String message,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledAt,
                                  RedirectAttributes redirectAttributes) {
        messageProducer.sendScheduledMessage(message, scheduledAt);
        redirectAttributes.addFlashAttribute("success", "Message scheduled at " + scheduledAt + ": " + message);
        return "redirect:/";
    }
}
