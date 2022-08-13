package com.food.order.system.order.service.domain.ports;

import com.food.order.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.order.system.order.service.domain.dto.track.TrackOrderResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class OrderTrackCommandHandler {

    TrackOrderResponse trackOrderResponse(TrackOrderQuery trackOrderQuery){

    }
}
