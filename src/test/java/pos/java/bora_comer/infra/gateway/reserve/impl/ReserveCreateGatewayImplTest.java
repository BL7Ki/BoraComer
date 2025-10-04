package pos.java.bora_comer.infra.gateway.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;
import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefault;

class ReserveCreateGatewayImplTest {

    private ReserveRepository reserveRepository;
    private ReserveMapper reserveMapper;
    private ReserveCreateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        reserveRepository = mock(ReserveRepository.class);
        reserveMapper = mock(ReserveMapper.class);
        gateway = new ReserveCreateGatewayImpl(reserveRepository, reserveMapper);
    }

    @Test
    @DisplayName("Deve lançar uma exceção se a reserva já existir para usuario naquela data e hora")
    void shouldThrowExceptionIfDateTimeReserveAndUserIdExists() {
        Reserve reserve = createDefault();

        when(reserveRepository.existsByDateTimeReserveAndUserId(reserve.getDateTimeReserve(), reserve.getUserId())).thenReturn(true);

        ReserveDomainException ex = assertThrows(ReserveDomainException.class, () -> gateway.save(reserve));
        assertEquals("Já existe uma reserva com esse usuario, data e hora.", ex.getMessage());

        verify(reserveRepository, times(1)).existsByDateTimeReserveAndUserId(reserve.getDateTimeReserve() , reserve.getUserId());
        verifyNoMoreInteractions(reserveRepository);
        verifyNoInteractions(reserveMapper);
    }

    @Test
    @DisplayName("Deve salvar e retornar a reserva")
    void shouldSaveAndReturnReserve() {
        Reserve reserve = createDefault();
        ReserveEntity entityToSave = new ReserveEntity();
        ReserveEntity savedEntity = new ReserveEntity();
        Reserve domainFromSaved = createDefault();

        when(reserveMapper.toEntity(reserve)).thenReturn(entityToSave);
        when(reserveRepository.save(entityToSave)).thenReturn(savedEntity);
        when(reserveMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        Reserve result = gateway.save(reserve);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());
        assertEquals(domainFromSaved.getUserId(), result.getUserId());
        assertEquals(domainFromSaved.getRestaurantId(), result.getRestaurantId());
        assertEquals(domainFromSaved.getQuantity(), result.getQuantity());
        assertEquals(domainFromSaved.getDateTimeReserve(), result.getDateTimeReserve());                
        
        verify(reserveMapper, times(1)).toEntity(reserve);
        verify(reserveRepository, times(1)).save(entityToSave);
        verify(reserveMapper, times(1)).toDomain(savedEntity);
    }
}
