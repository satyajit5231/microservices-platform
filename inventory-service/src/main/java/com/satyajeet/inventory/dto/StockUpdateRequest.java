package com.satyajeet.inventory.dto;
import lombok.Data;
@Data
public class StockUpdateRequest {
    private String sku;
    private Integer quantity;
}
