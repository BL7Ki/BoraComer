package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoDeleteGatewayImplTest {

    private PedidoRepository pedidoRepository;
    private PedidoDeleteGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoRepository = mock(PedidoRepository.class);
        gateway = new PedidoDeleteGatewayImpl(pedidoRepository);
    }

    @Test
    @DisplayName("Deve deletar o pedido quando encontrado")
    void shouldDeletePedidoWhenFound() {
        Long pedidoId = 1L;
        PedidoEntity entity = mock(PedidoEntity.class);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(entity));

        // Action
        assertDoesNotThrow(() -> gateway.deleteById(pedidoId));

        // Verify repository delete was called
        verify(pedidoRepository, times(1)).delete(entity);
        verify(pedidoRepository, times(1)).findById(pedidoId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o pedido não for encontrado")
    void shouldThrowExceptionWhenNotFound() {
        Long pedidoId = 10L;

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.deleteById(pedidoId));
        assertEquals("Pedido com ID " + pedidoId + " não encontrado.", ex.getMessage());

        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(pedidoRepository, never()).delete(any());
    }
}
