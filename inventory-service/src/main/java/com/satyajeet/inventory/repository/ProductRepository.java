package com.satyajeet.inventory.repository;
import com.satyajeet.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    List<Product> findByCategory(String category);
    List<Product> findByQuantityLessThan(Integer threshold);
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findInStockProducts();
}
