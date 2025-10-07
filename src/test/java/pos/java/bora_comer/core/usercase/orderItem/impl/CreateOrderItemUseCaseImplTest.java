package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemCreateGateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefault;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefaultWithId;

class CreateOrderItemUseCaseImplTest {

    private OrderItemCreateGateway orderItemCreateGateway;
    private CreateOrderItemUseCaseImpl createOrderItemUseCase;

    @BeforeEach
    void setUp() {
        orderItemCreateGateway = Mockito.mock(OrderItemCreateGateway.class);
        createOrderItemUseCase = new CreateOrderItemUseCaseImpl(orderItemCreateGateway);
    }

    @Test
    void execute_shouldReturnCreatedOrder() {
        // Arrange
        OrderItem orderItemToSave = createDefault();

        OrderItem savedOrderItem = createDefaultWithId();

        when(orderItemCreateGateway.save(any(OrderItem.class))).thenReturn(savedOrderItem);

        // Act
        OrderItem result = createOrderItemUseCase.execute(orderItemToSave);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(orderItemToSave.getMenuItemId(), result.getMenuItemId());
        assertEquals(orderItemToSave.getOrderId(), result.getOrderId());
        assertEquals(orderItemToSave.getQuantity(), result.getQuantity());
        verify(orderItemCreateGateway, times(1)).save(orderItemToSave);
    }
}
