package com.satyajeet.inventory.dto;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class ProductRequest {
    private String name;
    private String sku;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer quantity;
}
