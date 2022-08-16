package com.food.order.system.order.service.domain.mapper;

import com.food.order.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.food.order.system.order.service.domain.dto.create.CreatedOrderResponseDTO;
import com.food.order.system.order.service.domain.dto.create.OrderAddressDTO;
import com.food.order.system.order.service.domain.dto.create.OrderItemDTO;
import com.food.order.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.domain.entity.Order;
import com.food.ordering.system.domain.entity.OrderItem;
import com.food.ordering.system.domain.entity.Product;
import com.food.ordering.system.domain.entity.Restaurant;
import com.food.ordering.system.domain.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommandDTO createOrderCommand){
        return Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItemDTO ->
                    new Product(new ProductId(orderItemDTO.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommandDTO createOrderCommand){
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(createDeliveryAddressToStreetAddress(createOrderCommand.getOrderAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .item(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<OrderItemDTO> orderItemsDTO) {
        return orderItemsDTO.stream()
                .map(orderItemDTO ->
                    OrderItem.builder()
                            .product(new Product(new ProductId(orderItemDTO.getProductId())))
                            .price(new Money(orderItemDTO.getPrice()))
                            .quantity(orderItemDTO.getQuantity())
                            .subtotal(new Money(orderItemDTO.getSubtotal()))
                            .build()
                ).collect(Collectors.toList());
    }

    private StreetAddress createDeliveryAddressToStreetAddress(OrderAddressDTO orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }

    public CreatedOrderResponseDTO orderToCreateOrderResponse(Order order, String message){
        return CreatedOrderResponseDTO.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }
}
