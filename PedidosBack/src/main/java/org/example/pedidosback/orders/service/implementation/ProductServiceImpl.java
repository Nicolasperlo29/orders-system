package org.example.pedidosback.orders.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.pedidosback.orders.DTO.ProductRequest;
import org.example.pedidosback.orders.domain.Product;
import org.example.pedidosback.orders.repository.ProductRepository;
import org.example.pedidosback.orders.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductRequest productRequest) {

        if (productRepository.existsByName(productRequest.getName())) {
            throw new IllegalArgumentException("Product already exists");
        }

        if (productRequest.getName() == null || productRequest.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (productRequest.getPrice() == null || productRequest.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
