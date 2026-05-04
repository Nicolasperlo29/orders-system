package org.example.pedidosback.orders.service;

import org.example.pedidosback.orders.DTO.ProductRequest;
import org.example.pedidosback.orders.DTO.ProductResponse;
import org.example.pedidosback.orders.domain.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequest product);

    List<ProductResponse> getProducts();
}
