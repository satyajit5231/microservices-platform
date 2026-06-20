package com.satyajeet.inventory.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_sku", columnList = "sku"),
    @Index(name = "idx_category", columnList = "category")
})
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String name;
    @Column(unique = true, nullable = false) private String sku;
    private String description;
    private String category;
    @Column(nullable = false) private BigDecimal price;
    @Column(nullable = false) private Integer quantity;
    @Column(name = "created_at") private LocalDateTime createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;
    @PrePersist protected void onCreate() { createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
