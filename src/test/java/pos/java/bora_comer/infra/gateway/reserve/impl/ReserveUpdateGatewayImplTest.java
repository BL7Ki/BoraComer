package pos.java.bora_comer.infra.gateway.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;
import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReserveUpdateGatewayImplTest {

    private ReserveRepository reserveRepository;
    private ReserveMapper reserveMapper;
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private ReserveUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        reserveRepository = mock(ReserveRepository.class);
        reserveMapper = mock(ReserveMapper.class);
        restaurantRepository = mock(RestaurantRepository.class);
        userRepository = mock(UserRepository.class);

        gateway = new ReserveUpdateGatewayImpl(reserveRepository, reserveMapper, restaurantRepository, userRepository);
    }

    @Test
    @DisplayName("Deve atualizar uma reserva com sucesso quando o restaurante não for alterado")
    void shouldUpdateReserveWhenRestaurantIdNotChanged() {
        Long id = 1L;
        Long restaurantId = 10L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        Reserve domainReserve = Reserve.create(
                id,
                dateTime,
                2,
                restaurantId,
                userId,
                dateTime
        );

        ReserveEntity entity = mock(ReserveEntity.class);
        when(reserveRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(restaurantId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mock()));

        ReserveEntity updatedEntity = mock(ReserveEntity.class);
        when(reserveRepository.save(entity)).thenReturn(updatedEntity);

        Reserve expectedDomain = mock(Reserve.class);
        when(reserveMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Reserve result = gateway.update(domainReserve);

        verify(restaurantRepository, never()).findById(anyLong());
        verify(entity, never()).updateRestaurantId(anyLong());
        verify(entity).updateUserId(userId);
        verify(entity).updateDateTimeReserve(dateTime);
        verify(entity).updateQuantity(2);
         

        verify(reserveRepository).save(entity);
        verify(reserveMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve atualizar uma reserva e alterar o restaurante quando o ID do restaurante for alterado")
    void shouldUpdateReserveAndChangeRestaurant() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        Reserve domainReserve = Reserve.create(
                id,
                dateTime,
                2,
                newRestaurantId,
                userId,
                dateTime
        );

        ReserveEntity entity = mock(ReserveEntity.class);
        when(reserveRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);

        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.of(mock()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mock()));

        ReserveEntity updatedEntity = mock(ReserveEntity.class);
        when(reserveRepository.save(entity)).thenReturn(updatedEntity);

        Reserve expectedDomain = mock(Reserve.class);
        when(reserveMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Reserve result = gateway.update(domainReserve);

        verify(restaurantRepository).findById(newRestaurantId);
        verify(entity).updateRestaurantId(newRestaurantId);
        verify(entity).updateUserId(userId);
        verify(entity).updateQuantity(2);
        verify(entity).updateDateTimeReserve(dateTime);
        verify(reserveRepository).save(entity);
        verify(reserveMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a reserva não for encontrado")
    void shouldThrowWhenReserveNotFound() {
        Long id = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        Reserve domainReserve = Reserve.create(
                id,
                dateTime,
                2,
                10L,
                1L,
                dateTime
        );

        when(reserveRepository.findById(id)).thenReturn(Optional.empty());

        when(reserveRepository.findById(id)).thenReturn(Optional.empty());

        ReserveDomainException ex = assertThrows(ReserveDomainException.class, () -> gateway.update(domainReserve));
        assertEquals("Reserva com ID " + id + " não encontrado.", ex.getMessage());

        verify(reserveRepository).findById(id);
        verifyNoMoreInteractions(reserveRepository, restaurantRepository, userRepository, reserveMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo restaurante não for encontrado")
    void shouldThrowWhenNewRestaurantNotFound() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;

        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);
        Reserve domainReserve = Reserve.create(
                id,
                dateTime,
                2,
                newRestaurantId,
                1L,
                dateTime
        );

        ReserveEntity entity = mock(ReserveEntity.class);
        when(reserveRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);

        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.empty());

        ReserveDomainException ex = assertThrows(ReserveDomainException.class, () -> gateway.update(domainReserve));
        assertEquals("Restaurante com ID " + newRestaurantId + " não encontrado.", ex.getMessage());

        verify(reserveRepository).findById(id);
        verify(restaurantRepository).findById(newRestaurantId);

        verifyNoMoreInteractions(reserveRepository, restaurantRepository, reserveMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo usuário não for encontrado")
    void shouldThrowWhenNewUserNotFound() {
        Long id = 1L;
        Long oldUserId = 10L;
        Long newUserId = 20L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);
        Reserve domainReserve = Reserve.create(
                id,
                dateTime,
                2,
                1L,
                newUserId,
                dateTime
        );

        ReserveEntity entity = mock(ReserveEntity.class);
        when(reserveRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getUserId()).thenReturn(oldUserId);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(mock()));
        when(userRepository.findById(newUserId)).thenReturn(Optional.empty());

        ReserveDomainException ex = assertThrows(ReserveDomainException.class, () -> gateway.update(domainReserve));
        assertEquals("Usuário com ID " + newUserId + " não encontrado.", ex.getMessage());

        verify(reserveRepository).findById(id);
        verify(userRepository).findById(newUserId);
        

        verifyNoMoreInteractions(reserveRepository, userRepository, reserveMapper);
    }
}
