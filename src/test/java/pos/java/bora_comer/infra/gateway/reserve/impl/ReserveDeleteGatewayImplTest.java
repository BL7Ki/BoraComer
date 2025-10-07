package pos.java.bora_comer.infra.gateway.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;
import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReserveDeleteGatewayImplTest {

    private ReserveRepository reserveRepository;
    private ReserveDeleteGatewayImpl gateway;

    @BeforeEach
    void setup() {
        reserveRepository = mock(ReserveRepository.class);
        gateway = new ReserveDeleteGatewayImpl(reserveRepository);
    }

    @Test
    @DisplayName("Deve deletar a reserva quando encontrado")
    void shouldDeleteReserveWhenFound() {
        Long reserveId = 1L;
        ReserveEntity entity = mock(ReserveEntity.class);

        when(reserveRepository.findById(reserveId)).thenReturn(Optional.of(entity));

        // Action
        assertDoesNotThrow(() -> gateway.deleteById(reserveId));

        // Verify repository delete was called
        verify(reserveRepository, times(1)).delete(entity);
        verify(reserveRepository, times(1)).findById(reserveId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a reserva não for encontrada")
    void shouldThrowExceptionWhenNotFound() {
        Long reserveId = 10L;

        when(reserveRepository.findById(reserveId)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.deleteById(reserveId));
        assertEquals("Reserva com ID " + reserveId + " não encontrada.", ex.getMessage());

        verify(reserveRepository, times(1)).findById(reserveId);
        verify(reserveRepository, never()).delete(any());
    }
}
