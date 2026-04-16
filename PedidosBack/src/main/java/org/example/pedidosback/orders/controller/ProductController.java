package org.example.pedidosback.orders.controller;

import lombok.RequiredArgsConstructor;
import org.example.pedidosback.orders.DTO.ProductRequest;
import org.example.pedidosback.orders.domain.Product;
import org.example.pedidosback.orders.service.OrderService;
import org.example.pedidosback.orders.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
}
