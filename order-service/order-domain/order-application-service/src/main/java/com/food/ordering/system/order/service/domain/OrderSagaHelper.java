package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.valueobject.OrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class OrderSagaHelper {

    private final OrderRepository orderRepository;

    public OrderSagaHelper(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public Order findOrder(String orderId) {
        return orderRepository.findById(new OrderId(UUID.fromString(orderId)))
                .orElseThrow(() -> {
                    log.error("Order with id: {} could not be found!", orderId);
                    return new OrderNotFoundException("Order with id: " + orderId + " could not found!");
                });
    }
}
