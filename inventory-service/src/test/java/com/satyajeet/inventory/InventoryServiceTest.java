package com.satyajeet.inventory;
import com.satyajeet.inventory.dto.*;
import com.satyajeet.inventory.entity.Product;
import com.satyajeet.inventory.repository.ProductRepository;
import com.satyajeet.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {
    @Mock ProductRepository productRepository;
    @InjectMocks InventoryService inventoryService;

    @Test
    void addProduct_ShouldReturnProductResponse() {
        ProductRequest req = new ProductRequest();
        req.setName("Laptop"); req.setSku("LAP001");
        req.setPrice(BigDecimal.valueOf(999.99)); req.setQuantity(50);
        Product saved = Product.builder().id(1L).name("Laptop").sku("LAP001")
            .price(BigDecimal.valueOf(999.99)).quantity(50).build();
        when(productRepository.save(any())).thenReturn(saved);
        ProductResponse res = inventoryService.addProduct(req);
        assertNotNull(res);
        assertEquals("Laptop", res.getName());
        assertEquals("LAP001", res.getSku());
    }

    @Test
    void reduceStock_ShouldReturnFalse_WhenInsufficientStock() {
        Product p = Product.builder().sku("LAP001").quantity(5).build();
        when(productRepository.findBySku("LAP001")).thenReturn(Optional.of(p));
        boolean result = inventoryService.reduceStock("LAP001", 10);
        assertFalse(result);
    }

    @Test
    void reduceStock_ShouldReduceQuantity_WhenStockSufficient() {
        Product p = Product.builder().sku("LAP001").quantity(50)
            .price(BigDecimal.TEN).build();
        when(productRepository.findBySku("LAP001")).thenReturn(Optional.of(p));
        when(productRepository.save(any())).thenReturn(p);
        boolean result = inventoryService.reduceStock("LAP001", 10);
        assertTrue(result);
        assertEquals(40, p.getQuantity());
    }
}
