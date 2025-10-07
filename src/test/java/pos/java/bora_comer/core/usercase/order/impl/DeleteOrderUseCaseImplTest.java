package pos.java.bora_comer.core.usercase.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.gateway.order.OrderDeleteGateway;

import static org.mockito.Mockito.*;

class DeleteOrderUseCaseImplTest {

    private OrderDeleteGateway orderDeleteGateway;
    private DeleteOrderUseCaseImpl deleteOrderUseCase;

    @BeforeEach
    void setUp() {
        orderDeleteGateway = Mockito.mock(OrderDeleteGateway.class);
        deleteOrderUseCase = new DeleteOrderUseCaseImpl(orderDeleteGateway);
    }

    @Test
    void execute_shouldCallDeleteByIdOnce() {
        // Arrange
        Long orderId = 5L;

        // Act
        deleteOrderUseCase.execute(orderId);

        // Assert
        verify(orderDeleteGateway, times(1)).deleteById(orderId);
    }
}
