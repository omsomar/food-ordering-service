package com.food.ordering.system.payment.service.dataaccess.creditentry.mapper;

import com.food.ordering.system.order.service.domain.valueobject.CustomerId;
import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.payment.service.dataaccess.creditentry.entity.CreditEntryEntity;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.valueobject.CreditEntryId;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataaccessMapper {

    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntry) {
        return CreditEntry.builder()
                .id(new CreditEntryId(creditEntry.getId()))
                .customerId(new CustomerId(creditEntry.getCustomerId()))
                .totalCreditAmount(new Money(creditEntry.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getId().getValue())
                .customerId(creditEntry.getCustomerId().getValue())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }
}
