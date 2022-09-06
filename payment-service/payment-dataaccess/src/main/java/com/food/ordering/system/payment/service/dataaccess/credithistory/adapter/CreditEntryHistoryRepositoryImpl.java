package com.food.ordering.system.payment.service.dataaccess.credithistory.adapter;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.service.dataaccess.credithistory.mapper.CreditEntryHistoryDataaccessMapper;
import com.food.ordering.system.payment.service.dataaccess.credithistory.repository.CreditEntryHistoryJpaRepository;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CreditEntryHistoryRepositoryImpl implements CreditHistoryRepository {

    private final CreditEntryHistoryJpaRepository creditEntryHistoryJpaRepository;
    private final CreditEntryHistoryDataaccessMapper creditEntryHistoryDataaccessMapper;
    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditEntryHistoryDataaccessMapper
                .creditEntryHistoryEntityToCreditHistory(
                        creditEntryHistoryJpaRepository.save(
                                creditEntryHistoryDataaccessMapper
                                        .creditHistoryToCreditEntryHistoryEntity(creditHistory)));
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        return creditEntryHistoryJpaRepository
                .findByCustomerId(customerId.getValue())
                .map(creditEntryHistoryEntities -> creditEntryHistoryEntities
                        .stream()
                        .map(creditEntryHistoryDataaccessMapper::creditEntryHistoryEntityToCreditHistory)
                        .collect(Collectors.toList()));
    }
}
