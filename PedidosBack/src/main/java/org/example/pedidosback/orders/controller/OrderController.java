package org.example.pedidosback.orders.controller;

import lombok.RequiredArgsConstructor;
import org.example.pedidosback.orders.DTO.OrderRequest;
import org.example.pedidosback.orders.DTO.OrderResponse;
import org.example.pedidosback.orders.domain.Order;
import org.example.pedidosback.orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }
}
