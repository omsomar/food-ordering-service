package com.food.order.system.order.service.domain.ports;

import com.food.order.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.food.order.system.order.service.domain.mapper.OrderDataMapper;
import com.food.order.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.order.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.order.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.food.ordering.system.domain.OrderDomainService;
import com.food.ordering.system.domain.entity.Order;
import com.food.ordering.system.domain.entity.Restaurant;
import com.food.ordering.system.domain.event.OrderCreatedEvent;
import com.food.ordering.system.domain.exception.OrderDomainException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommandDTO createOrderCommandDTO) {
        checkCustomer(createOrderCommandDTO.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommandDTO);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommandDTO);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(CreateOrderCommandDTO createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        return restaurantRepository.findRestaurantInformation(restaurant.getId()).orElseThrow(() -> {
            log.error("Could not find the restaurant with id {}", restaurant.getId());
            throw new OrderDomainException("Could not find the restaurant with id: " + restaurant.getId());
        });
    }

    private void checkCustomer(UUID customerId) {
        customerRepository.findCustomer(customerId).orElseThrow( () -> {
            log.warn("Could not find customer with id: {}", customerId);
            throw new OrderDomainException("Could not find customer with id: " + customerId);
        });
    }

    private Order saveOrder(Order order){
        Order orderResult = orderRepository.save(order);
        if(orderResult == null) {
            log.warn("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return order;
    }
}
