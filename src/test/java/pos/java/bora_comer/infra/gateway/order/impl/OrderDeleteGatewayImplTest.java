package pos.java.bora_comer.infra.gateway.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderDeleteGatewayImplTest {

    private OrderRepository orderRepository;
    private OrderDeleteGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        gateway = new OrderDeleteGatewayImpl(orderRepository);
    }

    @Test
    @DisplayName("Deve deletar o pedido quando encontrado")
    void shouldDeleteOrderWhenFound() {
        Long orderId = 1L;
        OrderEntity entity = mock(OrderEntity.class);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

        // Action
        assertDoesNotThrow(() -> gateway.deleteById(orderId));

        // Verify repository delete was called
        verify(orderRepository, times(1)).delete(entity);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o pedido não for encontrado")
    void shouldThrowExceptionWhenNotFound() {
        Long orderId = 10L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.deleteById(orderId));
        assertEquals("Pedido com ID " + orderId + " não encontrado.", ex.getMessage());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).delete(any());
    }
}
