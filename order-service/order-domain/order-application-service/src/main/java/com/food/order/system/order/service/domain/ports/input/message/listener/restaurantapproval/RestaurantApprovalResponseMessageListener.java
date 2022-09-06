package com.food.order.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.food.order.system.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovedResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovedResponse);
}
