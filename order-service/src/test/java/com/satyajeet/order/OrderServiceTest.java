package com.satyajeet.order;
import com.satyajeet.order.dto.*;
import com.satyajeet.order.entity.Order;
import com.satyajeet.order.repository.OrderRepository;
import com.satyajeet.order.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock OrderRepository orderRepository;
    @Mock RestTemplate restTemplate;
    @Mock NotificationService notificationService;
    @InjectMocks OrderService orderService;

    @Test
    void placeOrder_ShouldCreateOrder_WhenStockAvailable() {
        when(restTemplate.exchange(anyString(), eq(org.springframework.http.HttpMethod.PUT), any(), eq(Boolean.class))).thenReturn(org.springframework.http.ResponseEntity.ok(true));        Order saved = Order.builder().id(1L).customerUsername("satyajeet")
            .productSku("LAP001").quantity(2).totalPrice(BigDecimal.valueOf(200))
            .status(Order.Status.PENDING).build();
        when(orderRepository.save(any())).thenReturn(saved);
        OrderRequest req = new OrderRequest();
        req.setProductSku("LAP001"); req.setQuantity(2);
        OrderResponse res = orderService.placeOrder(req, "satyajeet");
        assertNotNull(res);
        assertEquals("LAP001", res.getProductSku());
    }

    @Test
    void getOrdersByUser_ShouldReturnUserOrders() {
        Order o = Order.builder().id(1L).customerUsername("satyajeet")
            .productSku("LAP001").quantity(1).totalPrice(BigDecimal.TEN)
            .status(Order.Status.CONFIRMED).build();
        when(orderRepository.findByCustomerUsername("satyajeet")).thenReturn(List.of(o));
        List<OrderResponse> orders = orderService.getOrdersByUser("satyajeet");
        assertEquals(1, orders.size());
    }
}
