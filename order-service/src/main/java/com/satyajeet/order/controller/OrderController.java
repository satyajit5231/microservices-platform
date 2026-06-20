package com.satyajeet.order.controller;
import com.satyajeet.order.dto.*;
import com.satyajeet.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/orders") @RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request, Authentication auth) {
        return ResponseEntity.status(201).body(orderService.placeOrder(request, auth.getName()));
    }
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication auth) {
        return ResponseEntity.ok(orderService.getOrdersByUser(auth.getName()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
