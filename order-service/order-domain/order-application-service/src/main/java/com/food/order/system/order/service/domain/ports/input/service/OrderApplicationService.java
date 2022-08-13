package com.food.order.system.order.service.domain.ports.input.service;

import com.food.order.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.food.order.system.order.service.domain.dto.create.CreatedOrderResponseDTO;
import com.food.order.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.order.system.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreatedOrderResponseDTO createOrder(@Valid CreateOrderCommandDTO createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
