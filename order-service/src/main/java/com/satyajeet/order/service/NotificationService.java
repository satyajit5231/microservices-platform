package com.satyajeet.order.service;
import com.satyajeet.order.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
@Service @RequiredArgsConstructor @Slf4j
public class NotificationService {
    private final RestTemplate restTemplate;
    @Async
    public void sendNotification(NotificationEvent event) {
        try {
            restTemplate.postForObject("http://notification-service/api/notifications/event", event, Void.class);
            log.info("Notification sent for order: {}", event.getOrderId());
        } catch (Exception e) {
            log.warn("Notification service unavailable: {}", e.getMessage());
        }
    }
}
