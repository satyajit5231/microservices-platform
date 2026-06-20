package com.satyajeet.order.service;
import com.satyajeet.order.dto.*;
import com.satyajeet.order.entity.Order;
import com.satyajeet.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor @Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final NotificationService notificationService;

    @Value("${inventory.service.url}") private String inventoryUrl;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request, String username) {
        // Check and reduce stock in inventory service
        String url = inventoryUrl + "/api/inventory/reduce?sku=" + request.getProductSku() + "&quantity=" + request.getQuantity();
        Boolean stockReduced = restTemplate.exchange(url, org.springframework.http.HttpMethod.PUT, null, Boolean.class).getBody();        if (stockReduced == null || !stockReduced)
            throw new RuntimeException("Insufficient stock for SKU: " + request.getProductSku());

        Order order = Order.builder()
            .customerUsername(username)
            .productSku(request.getProductSku())
            .quantity(request.getQuantity())
            .totalPrice(BigDecimal.valueOf(request.getQuantity() * 100L)) // simplified price
            .build();
        order = orderRepository.save(order);

        // Async notification event
        notificationService.sendNotification(new NotificationEvent(
            "ORDER_PLACED", username, order.getId(), request.getProductSku(), "CONFIRMED"));

        return toResponse(order);
    }

    public List<OrderResponse> getOrdersByUser(String username) {
        return orderRepository.findByCustomerUsername(username)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        return toResponse(orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found")));
    }

    private OrderResponse toResponse(Order o) {
        return OrderResponse.builder()
            .id(o.getId()).customerUsername(o.getCustomerUsername())
            .productSku(o.getProductSku()).quantity(o.getQuantity())
            .totalPrice(o.getTotalPrice()).status(o.getStatus().name())
            .createdAt(o.getCreatedAt()).build();
    }
}
