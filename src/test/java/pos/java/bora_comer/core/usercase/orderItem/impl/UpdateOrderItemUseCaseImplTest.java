package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefaultWithId;

class UpdateOrderItemUseCaseImplTest {

    private OrderItemUpdateGateway orderItemUpdateGateway;
    private UpdateOrderItemUseCaseImpl updateOrderItemUseCase;

    @BeforeEach
    void setUp() {
        orderItemUpdateGateway = Mockito.mock(OrderItemUpdateGateway.class);
        updateOrderItemUseCase = new UpdateOrderItemUseCaseImpl(orderItemUpdateGateway);
    }

    @Test
    void execute_shouldUpdateAndReturnOrderItem() {
        // Arrange
        OrderItem orderItemToUpdate = createDefaultWithId();

        when(orderItemUpdateGateway.update(orderItemToUpdate)).thenReturn(orderItemToUpdate);

        // Act
        OrderItem updatedOrderItem = updateOrderItemUseCase.execute(orderItemToUpdate);

        // Assert
        assertThat(updatedOrderItem).isNotNull();
        verify(orderItemUpdateGateway, times(1)).update(orderItemToUpdate);
    }
}
