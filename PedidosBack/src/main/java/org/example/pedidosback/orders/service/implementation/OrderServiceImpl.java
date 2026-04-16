package org.example.pedidosback.orders.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.pedidosback.orders.DTO.OrderItemRequest;
import org.example.pedidosback.orders.DTO.OrderItemResponse;
import org.example.pedidosback.orders.DTO.OrderRequest;
import org.example.pedidosback.orders.DTO.OrderResponse;
import org.example.pedidosback.orders.domain.Order;
import org.example.pedidosback.orders.domain.OrderItem;
import org.example.pedidosback.orders.domain.Product;
import org.example.pedidosback.orders.repository.OrderItemRepository;
import org.example.pedidosback.orders.repository.OrderRepository;
import org.example.pedidosback.orders.repository.ProductRepository;
import org.example.pedidosback.orders.service.OrderService;
import org.example.pedidosback.users.domain.User;
import org.example.pedidosback.users.repository.UserRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setDelivery(orderRequest.isDelivery());
        order.setDate(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setOrder(order);

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            total = total.add(itemTotal);

            items.add(item);
        }

        order.setItems(items);
        order.setTotal(total);
        order.setOrderNumber(System.currentTimeMillis());

        Order savedOrder = orderRepository.save(order);
        
        List<OrderItemResponse> itemsResponse = new ArrayList<>();

        for (OrderItem item : savedOrder.getItems()) {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setProductId(item.getProduct().getId());
            itemResponse.setQuantity(item.getQuantity());

            itemsResponse.add(itemResponse);
        }

        return new OrderResponse(
                savedOrder.getOrderNumber(),
                savedOrder.getUser().getId(),
                itemsResponse,
                savedOrder.isDelivery(),
                savedOrder.getDate()
        );
    }

    @Override
    public List<OrderResponse> getOrders() {

        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> ordersResponse = new ArrayList<>();

        for (Order o : orders) {

            List<OrderItemResponse> itemsResponse = new ArrayList<>();

            for (OrderItem item : o.getItems()) {

                OrderItemResponse itemResponse = new OrderItemResponse();
                itemResponse.setProductId(item.getProduct().getId());
                itemResponse.setQuantity(item.getQuantity());

                itemsResponse.add(itemResponse);
            }

            ordersResponse.add(new OrderResponse(
                    o.getOrderNumber(),
                    o.getUser().getId(),
                    itemsResponse,
                    o.isDelivery(),
                    o.getDate()
            ));
        }

        return ordersResponse;
    }
}
