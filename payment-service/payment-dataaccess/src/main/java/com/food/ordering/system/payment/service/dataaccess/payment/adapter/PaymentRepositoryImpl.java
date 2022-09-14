package com.food.ordering.system.payment.service.dataaccess.payment.adapter;

import com.food.ordering.system.payment.service.dataaccess.payment.mapper.PaymentDataaccessMapper;
import com.food.ordering.system.payment.service.dataaccess.payment.repository.PaymentJpaRepository;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.ports.output.repository.PaymentRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentRespository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataaccessMapper paymentDataaccessMapper;

    @Override
    public Payment save(Payment payment) {
        return paymentDataaccessMapper
                .paymentEntityToPayment(
                        paymentJpaRepository.save(
                                paymentDataaccessMapper
                                        .paymentToPaymentEntity(payment)
                        )
                );
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrdenId(orderId).map(paymentDataaccessMapper::paymentEntityToPayment);
    }
}
