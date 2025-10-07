package pos.java.bora_comer.core.usercase.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.gateway.order.OrderCreateGateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefault;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;

class CreateOrderUseCaseImplTest {

    private OrderCreateGateway orderCreateGateway;
    private CreateOrderUseCaseImpl createOrderUseCase;

    @BeforeEach
    void setUp() {
        orderCreateGateway = Mockito.mock(OrderCreateGateway.class);
        createOrderUseCase = new CreateOrderUseCaseImpl(orderCreateGateway);
    }

    @Test
    void execute_shouldReturnCreatedOrder() {
        // Arrange
        Order orderToSave = createDefault();

        Order savedOrder = createDefaultWithId();

        when(orderCreateGateway.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = createOrderUseCase.execute(orderToSave);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(orderToSave.getUserId(), result.getUserId());
        assertEquals(orderToSave.getRestaurantId(), result.getRestaurantId());
        assertEquals(orderToSave.isDelivery(), result.isDelivery());
        verify(orderCreateGateway, times(1)).save(orderToSave);
    }
}
