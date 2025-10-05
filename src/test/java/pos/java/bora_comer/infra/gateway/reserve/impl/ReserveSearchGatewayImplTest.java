package pos.java.bora_comer.infra.gateway.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReserveSearchGatewayImplTest {

    private ReserveRepository reserveRepository;
    private ReserveMapper reserveMapper;
    private ReserveSearchGatewayImpl gateway;

    @BeforeEach
    void setup() {
        reserveRepository = mock(ReserveRepository.class);
        reserveMapper = mock(ReserveMapper.class);
        gateway = new ReserveSearchGatewayImpl(reserveRepository, reserveMapper);
    }

    @Test
    @DisplayName("Achar uma reserva por ID deve retornar a reserva quando encontrado")
    void findById_shouldReturnReserve_whenFound() {
        Long id = 10L;
        pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity entity = mock(pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity.class);
        when(entity.getId()).thenReturn(id);
        Reserve domain = mock(Reserve.class);

        when(reserveRepository.findById(id)).thenReturn(Optional.of(entity));
        when(reserveMapper.toDomain(entity)).thenReturn(domain);

        Reserve result = gateway.findById(id);

        assertSame(domain, result);
        verify(reserveRepository).findById(id);
        verify(reserveMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Achar uma reserva por ID deve lançar SummerNotFoundException quando não encontrada")
    void findById_shouldThrow_whenNotFound() {
        Long id = 1L;

        when(reserveRepository.findById(id)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.findById(id));
        assertEquals("Reserva com ID " + id + " não encontrada.", ex.getMessage());

        verify(reserveRepository).findById(id);
        verifyNoInteractions(reserveMapper);
    }

    @Test
    @DisplayName("achar todos as reservas deve retornar uma lista reservas")
    void findAll_shouldReturnPagedReserves() {
        int page = 0;
        int size = 2;

        // Criando mocks das entidades reais
        pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity entity1 = mock(pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity.class);
        pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity entity2 = mock(pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity.class);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), pageRequest, 2);

        // Criando mocks dos domínios convertidos
        Reserve domain1 = mock(Reserve.class);
        Reserve domain2 = mock(Reserve.class);

        when(reserveRepository.findAll(pageRequest)).thenReturn(entityPage);
        when(reserveMapper.toDomain(entity1)).thenReturn(domain1);
        when(reserveMapper.toDomain(entity2)).thenReturn(domain2);

        Page<Reserve> resultPage = gateway.findAll(page, size);

        assertEquals(2, resultPage.getContent().size());
        assertTrue(resultPage.getContent().containsAll(List.of(domain1, domain2)));

        verify(reserveRepository).findAll(pageRequest);
        verify(reserveMapper).toDomain(entity1);
        verify(reserveMapper).toDomain(entity2);
    }

}
