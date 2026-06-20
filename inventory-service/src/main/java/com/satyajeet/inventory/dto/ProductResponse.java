package com.satyajeet.inventory.dto;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime createdAt;
}
