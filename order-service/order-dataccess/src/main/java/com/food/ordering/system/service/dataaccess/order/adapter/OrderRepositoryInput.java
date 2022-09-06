package com.food.ordering.system.service.dataaccess.order.adapter;

import com.food.order.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.domain.entity.Order;
import com.food.ordering.system.domain.valueobject.TrackingId;
import com.food.ordering.system.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.food.ordering.system.service.dataaccess.order.repository.OrderJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class OrderRepositoryInput implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(orderJpaRepository
                .save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
