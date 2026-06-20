package com.satyajeet.order.dto;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String customerUsername;
    private String productSku;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
