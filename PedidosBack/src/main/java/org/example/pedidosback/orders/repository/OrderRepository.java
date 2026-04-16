package org.example.pedidosback.orders.repository;

import org.example.pedidosback.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByOrderNumber(Long orderNumber);
}
