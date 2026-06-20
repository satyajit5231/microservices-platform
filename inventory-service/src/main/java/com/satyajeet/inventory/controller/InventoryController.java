package com.satyajeet.inventory.controller;
import com.satyajeet.inventory.dto.*;
import com.satyajeet.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/inventory") @RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.status(201).body(inventoryService.addProduct(request));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(inventoryService.getAllProducts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getProductById(id));
    }
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponse> getBySku(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.getProductBySku(sku));
    }
    @PutMapping("/stock")
    public ResponseEntity<ProductResponse> updateStock(@RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(inventoryService.updateStock(request));
    }
    @PutMapping("/reduce")
    public ResponseEntity<Boolean> reduceStock(@RequestParam String sku, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.reduceStock(sku, quantity));
    }
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponse>> getLowStock(@RequestParam(defaultValue = "10") Integer threshold) {
        return ResponseEntity.ok(inventoryService.getLowStockProducts(threshold));
    }
}
