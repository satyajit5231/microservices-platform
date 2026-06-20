package com.satyajeet.order.dto;
import lombok.Data;
@Data
public class OrderRequest {
    private String productSku;
    private Integer quantity;
}
