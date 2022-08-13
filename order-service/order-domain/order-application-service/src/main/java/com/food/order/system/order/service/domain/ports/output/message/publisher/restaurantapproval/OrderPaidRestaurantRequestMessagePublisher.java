package com.food.order.system.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.food.ordering.system.domain.event.OrderCancelledEvent;
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}