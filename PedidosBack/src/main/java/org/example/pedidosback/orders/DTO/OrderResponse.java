package org.example.pedidosback.orders.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long orderNumber;

    private Long userId;

    private List<OrderItemResponse> items;

    private boolean isDelivery;

    private LocalDateTime date;
}
