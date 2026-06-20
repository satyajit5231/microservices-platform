package com.satyajeet.notification.controller;
import com.satyajeet.notification.dto.NotificationEvent;
import com.satyajeet.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/notifications") @RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/event")
    public ResponseEntity<Void> handleEvent(@RequestBody NotificationEvent event) {
        notificationService.processEvent(event);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Notification service is running");
    }
}
