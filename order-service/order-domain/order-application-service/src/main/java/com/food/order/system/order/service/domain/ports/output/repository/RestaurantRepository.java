package com.food.order.system.order.service.domain.ports.output.repository;

import com.food.ordering.system.domain.entity.Restaurant;
import com.food.ordering.system.domain.valueobject.RestaurantId;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(RestaurantId restaurantId);
}
