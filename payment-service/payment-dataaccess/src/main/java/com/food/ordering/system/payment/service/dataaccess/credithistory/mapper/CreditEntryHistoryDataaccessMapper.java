package com.food.ordering.system.payment.service.dataaccess.credithistory.mapper;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.payment.service.dataaccess.credithistory.entity.CreditEntryHistoryEntity;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryHistoryDataaccessMapper {
    public CreditEntryHistoryEntity creditHistoryToCreditEntryHistoryEntity(CreditHistory creditHistory) {
        return CreditEntryHistoryEntity.builder()
                .id(creditHistory.getId().getValue())
                .customerId(creditHistory.getCustomerId().getValue())
                .amount(creditHistory.getAmount().getAmount())
                .type(creditHistory.getTransactionType())
                .build();
    }

    public CreditHistory creditEntryHistoryEntityToCreditHistory(CreditEntryHistoryEntity creditEntryHistoryEntity) {
        return CreditHistory.builder()
                .id(new CreditHistoryId(creditEntryHistoryEntity.getId()))
                .customerId(new CustomerId(creditEntryHistoryEntity.getCustomerId()))
                .transactionType(creditEntryHistoryEntity.getType())
                .amount(new Money(creditEntryHistoryEntity.getAmount()))
                .build();

    }
}
