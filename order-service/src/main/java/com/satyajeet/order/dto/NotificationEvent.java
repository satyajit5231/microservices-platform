package com.satyajeet.order.dto;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class NotificationEvent {
    private String type;
    private String username;
    private Long orderId;
    private String productSku;
    private String status;
}
