package com.food.order.system.order.service.domain;

import com.food.order.system.order.service.domain.dto.message.RestaurantApprovedResponse;
import com.food.order.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalMessageListener {
    @Override
    public void orderApproved(RestaurantApprovedResponse restaurantApprovedResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovedResponse restaurantApprovedResponse) {

    }
}
