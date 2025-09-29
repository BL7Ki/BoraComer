package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.gateway.orderItem.OrderItemDeleteGateway;

import static org.mockito.Mockito.*;

class DeleteOrderItemUseCaseImplTest {

    private OrderItemDeleteGateway orderItemDeleteGateway;
    private DeleteOrderItemUseCaseImpl deleteOrderItemUseCase;

    @BeforeEach
    void setUp() {
        orderItemDeleteGateway = Mockito.mock(OrderItemDeleteGateway.class);
        deleteOrderItemUseCase = new DeleteOrderItemUseCaseImpl(orderItemDeleteGateway);
    }

    @Test
    void execute_shouldCallDeleteByIdOnce() {
        // Arrange
        Long orderItemId = 5L;

        // Act
        deleteOrderItemUseCase.execute(orderItemId);

        // Assert
        verify(orderItemDeleteGateway, times(1)).deleteById(orderItemId);
    }
}
