package com.food.ordering.system.order.service.domain;

import com.food.order.system.order.service.domain.OrderApplicationServiceImpl;
import com.food.order.system.order.service.domain.OrderCreateCommandHandler;
import com.food.order.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.food.order.system.order.service.domain.dto.create.CreatedOrderResponseDTO;
import com.food.order.system.order.service.domain.dto.create.OrderAddressDTO;
import com.food.order.system.order.service.domain.dto.create.OrderItemDTO;
import com.food.order.system.order.service.domain.mapper.OrderDataMapper;
import com.food.order.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.food.order.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.order.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.order.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.food.ordering.system.domain.entity.*;
import com.food.ordering.system.domain.valueobject.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
class OrderApplicationServiceTest {

    @InjectMocks
    private OrderDataMapper orderDataMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OrderApplicationService orderApplicationService;

    private CreateOrderCommandDTO createOrderCommand;
    private CreateOrderCommandDTO createOrderCommandWrongPrice;
    private CreateOrderCommandDTO createOrderCommandWrongProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("f9f772d8-1c93-4575-a90a-d30af201439a");
    private final UUID RESTAURANT_ID = UUID.fromString("99c34800-6562-4191-a5c9-fe20681a3dc5");
    private final UUID PRODUCT_ID = UUID.fromString("8468cbe2-db14-4565-aa0a-7949f5a5bf96");
    private final UUID ORDER_ID = UUID.fromString("09bf3150-b8dc-4f94-befb-f30f8ce88143");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    void init() {
        openMocks(this);
        createOrderCommand = CreateOrderCommandDTO.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddressDTO.builder()
                        .street("street_1")
                        .postalCode("100AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItemDTO.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subtotal(new BigDecimal("50.00"))
                        .build(),
                        OrderItemDTO.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subtotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        createOrderCommandWrongPrice = CreateOrderCommandDTO.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddressDTO.builder()
                        .street("street_1")
                        .postalCode("100AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItemDTO.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subtotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItemDTO.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subtotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurant = Restaurant.builder()
                .id(new RestaurantId(RESTAURANT_ID))
                .products(List.of(
                        new Product(
                            new ProductId(PRODUCT_ID),
                        "product-1",
                            new Money(new BigDecimal("50.00"))
                        ),
                        new Product(
                                new ProductId(PRODUCT_ID),
                                "product-2",
                                new Money(new BigDecimal("50.00")))
                                ))
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(
                orderDataMapper.createOrderCommandToRestaurant(
                        createOrderCommand)
                        .getId()
        )).thenReturn(Optional.of(restaurant));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    void testCreateOrder() {
        CreatedOrderResponseDTO createdOrderResponseDTO = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createdOrderResponseDTO.getOrderStatus());
        assertEquals("Order created successfully", createdOrderResponseDTO.getMessage());
        assertNotNull(createdOrderResponseDTO.getOrderTrackingId());
    }
}
