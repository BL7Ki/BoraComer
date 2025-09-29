package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoItemDeleteGatewayImplTest {

    private PedidoItemRepository pedidoItemRepository;
    private PedidoItemDeleteGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoItemRepository = mock(PedidoItemRepository.class);
        gateway = new PedidoItemDeleteGatewayImpl(pedidoItemRepository);
    }

    @Test
    @DisplayName("Deve deletar o item de pedido quando encontrado")
    void shouldDeletePedidoItemWhenFound() {
        Long pedidoItemId = 1L;
        PedidoItemEntity entity = mock(PedidoItemEntity.class);

        when(pedidoItemRepository.findById(pedidoItemId)).thenReturn(Optional.of(entity));

        // Action
        assertDoesNotThrow(() -> gateway.deleteById(pedidoItemId));

        // Verify repository delete was called
        verify(pedidoItemRepository, times(1)).delete(entity);
        verify(pedidoItemRepository, times(1)).findById(pedidoItemId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item de pedido não for encontrado")
    void shouldThrowExceptionWhenNotFound() {
        Long pedidoItemId = 10L;

        when(pedidoItemRepository.findById(pedidoItemId)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.deleteById(pedidoItemId));
        assertEquals("Item de Pedido com ID " + pedidoItemId + " não encontrado.", ex.getMessage());

        verify(pedidoItemRepository, times(1)).findById(pedidoItemId);
        verify(pedidoItemRepository, never()).delete(any());
    }
}
