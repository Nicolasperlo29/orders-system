package org.example.pedidosback.orders.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pedidosback.orders.domain.OrderItem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long orderNumber;

    private Long userId;

    private List<OrderItemRequest> items;

    private boolean isDelivery;
}
