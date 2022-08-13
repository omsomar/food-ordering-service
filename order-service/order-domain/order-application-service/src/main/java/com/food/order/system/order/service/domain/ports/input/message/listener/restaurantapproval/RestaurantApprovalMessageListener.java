package com.food.order.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.food.order.system.order.service.domain.dto.message.RestaurantApprovedResponse;

public interface RestaurantApprovalMessageListener {

    void orderApproved(RestaurantApprovedResponse restaurantApprovedResponse);

    void orderRejected(RestaurantApprovedResponse restaurantApprovedResponse);
}
