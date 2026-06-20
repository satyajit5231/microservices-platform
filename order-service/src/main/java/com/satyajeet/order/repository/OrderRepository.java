package com.satyajeet.order.repository;
import com.satyajeet.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerUsername(String username);
    List<Order> findByStatus(Order.Status status);
}
