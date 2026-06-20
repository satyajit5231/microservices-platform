package com.satyajeet.order.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name = "orders")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String customerUsername;
    @Column(nullable = false) private String productSku;
    @Column(nullable = false) private Integer quantity;
    @Column(nullable = false) private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) private Status status;
    @Column(name = "created_at") private LocalDateTime createdAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); status = Status.PENDING; }
    public enum Status { PENDING, CONFIRMED, CANCELLED }
}
