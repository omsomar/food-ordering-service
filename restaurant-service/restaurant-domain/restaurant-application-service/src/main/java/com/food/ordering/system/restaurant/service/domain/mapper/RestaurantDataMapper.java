package com.food.ordering.system.restaurant.service.domain.mapper;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.order.service.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.service.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.entity.OrderDetail;
import com.food.ordering.system.restaurant.domain.entity.Product;
import com.food.ordering.system.restaurant.domain.entity.Restaurant;
import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataMapper {

    public Restaurant restaurantApprovalRequestAvroModelToRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        return Restaurant.builder()
                .id(new RestaurantId(UUID.fromString(restaurantApprovalRequest.getRestaurantId())))
                .orderDetail(OrderDetail.builder()
                        .id(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())))
                        .products(restaurantApprovalRequest.getProducts().stream().map(
                                product -> Product.builder()
                                        .id(product.getId())
                                        .quantity(product.getQuantity())
                                        .build())
                                .collect(Collectors.toList()))
                        .totalAmount(new Money(restaurantApprovalRequest.getPrice()))
                        .orderStatus(OrderStatus.valueOf(restaurantApprovalRequest.getRestaurantOrderStatus().name()))
                        .build())
                .build();
    }
}