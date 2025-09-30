package pos.java.bora_comer.core.usercase.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.gateway.order.OrderUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;

class UpdateOrderUseCaseImplTest {

    private OrderUpdateGateway orderUpdateGateway;
    private UpdateOrderUseCaseImpl updateOrderUseCase;

    @BeforeEach
    void setUp() {
        orderUpdateGateway = Mockito.mock(OrderUpdateGateway.class);
        updateOrderUseCase = new UpdateOrderUseCaseImpl(orderUpdateGateway);
    }

    @Test
    void execute_shouldUpdateAndReturnOrder() {
        // Arrange
        Order orderToUpdate = createDefaultWithId();

        when(orderUpdateGateway.update(orderToUpdate)).thenReturn(orderToUpdate);

        // Act
        Order updatedOrder = updateOrderUseCase.execute(orderToUpdate);

        // Assert
        assertThat(updatedOrder).isNotNull();
        verify(orderUpdateGateway, times(1)).update(orderToUpdate);
    }
}
