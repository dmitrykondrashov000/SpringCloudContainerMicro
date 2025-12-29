package org.example.microsmtp.service;

import org.example.microsmtp.event.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerService {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events-json111", groupId = "notification-service")
    public void handleUserEvent(UserEvent event) {
        log.info("Получено Kafka сообщение: {}", event);
        if ("CREATE".equalsIgnoreCase(event.getOperation())) {
            emailService.sendAccountCreated(event.getEmail());
        } else if ("DELETE".equalsIgnoreCase(event.getOperation())) {
            emailService.sendAccountDeleted(event.getEmail());
        }
    }
}