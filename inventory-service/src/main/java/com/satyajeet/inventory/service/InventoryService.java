package com.satyajeet.inventory.service;
import com.satyajeet.inventory.dto.*;
import com.satyajeet.inventory.entity.Product;
import com.satyajeet.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor
public class InventoryService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse addProduct(ProductRequest request) {
        Product product = Product.builder()
            .name(request.getName()).sku(request.getSku())
            .description(request.getDescription()).category(request.getCategory())
            .price(request.getPrice()).quantity(request.getQuantity())
            .build();
        return toResponse(productRepository.save(product));
    }

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductById(Long id) {
        Product p = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return toResponse(p);
    }

    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Cacheable(value = "products", key = "#sku")
    public ProductResponse getProductBySku(String sku) {
        return toResponse(productRepository.findBySku(sku)
            .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public ProductResponse updateStock(StockUpdateRequest request) {
        Product product = productRepository.findBySku(request.getSku())
            .orElseThrow(() -> new RuntimeException("Product not found: " + request.getSku()));
        product.setQuantity(request.getQuantity());
        return toResponse(productRepository.save(product));
    }

    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public boolean reduceStock(String sku, Integer quantity) {
        Product product = productRepository.findBySku(sku)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getQuantity() < quantity) return false;
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return true;
    }

    public List<ProductResponse> getLowStockProducts(Integer threshold) {
        return productRepository.findByQuantityLessThan(threshold)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
            .id(p.getId()).name(p.getName()).sku(p.getSku())
            .description(p.getDescription()).category(p.getCategory())
            .price(p.getPrice()).quantity(p.getQuantity())
            .createdAt(p.getCreatedAt()).build();
    }
}
