package com.food.ordering.system.service.dataaccess.customer.adapter;

import com.food.order.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.domain.entity.Customer;
import com.food.ordering.system.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.food.ordering.system.service.dataaccess.customer.repository.CustomerJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;
    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
