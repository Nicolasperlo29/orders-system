package org.example.pedidosback.orders.service;

import org.example.pedidosback.orders.DTO.OrderRequest;
import org.example.pedidosback.orders.DTO.OrderResponse;
import org.example.pedidosback.orders.domain.Order;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    List<OrderResponse> getOrders();
}
