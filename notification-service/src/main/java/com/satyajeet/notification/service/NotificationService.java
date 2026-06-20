package com.satyajeet.notification.service;
import com.satyajeet.notification.dto.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service @Slf4j
public class NotificationService {
    public void processEvent(NotificationEvent event) {
        log.info("Processing notification: type={}, user={}, orderId={}",
            event.getType(), event.getUsername(), event.getOrderId());
        switch (event.getType()) {
            case "ORDER_PLACED" ->
                log.info("Email sent to {} — Order #{} placed for SKU: {}",
                    event.getUsername(), event.getOrderId(), event.getProductSku());
            case "ORDER_CANCELLED" ->
                log.info("Email sent to {} — Order #{} cancelled", event.getUsername(), event.getOrderId());
            default -> log.warn("Unknown event type: {}", event.getType());
        }
    }
}
