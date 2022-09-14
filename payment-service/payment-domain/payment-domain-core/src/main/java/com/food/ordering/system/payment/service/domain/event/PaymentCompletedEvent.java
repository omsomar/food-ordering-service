package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.order.service.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class PaymentCompletedEvent extends PaymentEvent {

    private final DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher;
    public PaymentCompletedEvent(Payment payment, ZonedDateTime createdAt,
                                 DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher) {
        super(payment, createdAt, new ArrayList<>());
        this.paymentCompletedEventDomainEventPublisher = paymentCompletedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        paymentCompletedEventDomainEventPublisher.publish(this);
    }
}
