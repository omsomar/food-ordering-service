package com.food.ordering.system.service.application.rest;

import com.food.order.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.food.order.system.order.service.domain.dto.create.CreatedOrderResponseDTO;
import com.food.order.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.order.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.order.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<CreatedOrderResponseDTO> createOrder(@RequestBody CreateOrderCommandDTO createOrderCommandDTO) {
        log.info("Creating order for customer: {} at restaurant: {}", createOrderCommandDTO.getCustomerId(), createOrderCommandDTO.getRestaurantId());
        CreatedOrderResponseDTO createdOrderResponseDTO = orderApplicationService.createOrder(createOrderCommandDTO);
        log.info("Order Created with tracking id: {}", createdOrderResponseDTO.getOrderTrackingId());
        return ResponseEntity.ok(createdOrderResponseDTO);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> createOrder(@PathVariable UUID trackingId) {
        TrackOrderResponse trackOrderResponse = orderApplicationService.
                trackOrder(TrackOrderQuery.builder().orderTrackingId(trackingId).build());
        log.info("Returning order status with tracking id: {}", trackOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResponse);
    }
}
