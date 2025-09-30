package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;
import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemDeleteGatewayImplTest {

    private OrderItemRepository orderItemRepository;
    private OrderItemDeleteGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderItemRepository = mock(OrderItemRepository.class);
        gateway = new OrderItemDeleteGatewayImpl(orderItemRepository);
    }

    @Test
    @DisplayName("Deve deletar o item de pedido quando encontrado")
    void shouldDeleteOrderItemWhenFound() {
        Long orderItemId = 1L;
        OrderItemEntity entity = mock(OrderItemEntity.class);

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(entity));

        // Action
        assertDoesNotThrow(() -> gateway.deleteById(orderItemId));

        // Verify repository delete was called
        verify(orderItemRepository, times(1)).delete(entity);
        verify(orderItemRepository, times(1)).findById(orderItemId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item de pedido não for encontrado")
    void shouldThrowExceptionWhenNotFound() {
        Long orderItemId = 10L;

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.deleteById(orderItemId));
        assertEquals("Item de Pedido com ID " + orderItemId + " não encontrado.", ex.getMessage());

        verify(orderItemRepository, times(1)).findById(orderItemId);
        verify(orderItemRepository, never()).delete(any());
    }
}
