package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.PedidoDomainException;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoUpdateGatewayImplTest {

    private PedidoRepository pedidoRepository;
    private PedidoMapper pedidoMapper;
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private PedidoUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoRepository = mock(PedidoRepository.class);
        pedidoMapper = mock(PedidoMapper.class);
        restaurantRepository = mock(RestaurantRepository.class);
        userRepository = mock(UserRepository.class);

        gateway = new PedidoUpdateGatewayImpl(pedidoRepository, pedidoMapper, restaurantRepository, userRepository);
    }

    @Test
    @DisplayName("Deve atualizar um pedido com sucesso quando o restaurante não for alterado")
    void shouldUpdatePedidoWhenRestaurantIdNotChanged() {
        Long id = 1L;
        Long restaurantId = 10L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        Pedido domainPedido = Pedido.create(
                id,
                dateTime,
                false,
                restaurantId,
                userId,
                dateTime
        );

        PedidoEntity entity = mock(PedidoEntity.class);
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(restaurantId);

        PedidoEntity updatedEntity = mock(PedidoEntity.class);
        when(pedidoRepository.save(entity)).thenReturn(updatedEntity);

        Pedido expectedDomain = mock(Pedido.class);
        when(pedidoMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Pedido result = gateway.update(domainPedido);

        verify(restaurantRepository, never()).findById(anyLong());
        verify(entity, never()).updateRestaurantId(anyLong());
        verify(entity).updateUserId(userId);
        verify(entity).updateDateTimeOrder(dateTime);
        verify(entity).updateDelivery(false);
        verify(entity).updateDateTimeOrder(dateTime);   

        verify(pedidoRepository).save(entity);
        verify(pedidoMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve atualizar um pedido e alterar o restaurante quando o ID do restaurante for alterado")
    void shouldUpdatePedidoAndChangeRestaurant() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;
        Long userId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        Pedido domainPedido = Pedido.create(
                id,
                dateTime,
                false,
                newRestaurantId,
                userId,
                dateTime
        );

        PedidoEntity entity = mock(PedidoEntity.class);
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);

        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.of(mock()));

        PedidoEntity updatedEntity = mock(PedidoEntity.class);
        when(pedidoRepository.save(entity)).thenReturn(updatedEntity);

        Pedido expectedDomain = mock(Pedido.class);
        when(pedidoMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Pedido result = gateway.update(domainPedido);

        verify(restaurantRepository).findById(newRestaurantId);
        verify(entity).updateRestaurantId(newRestaurantId);

        verify(entity).updateUserId(userId);
        verify(entity).updateDateTimeOrder(dateTime);
        verify(entity).updateDelivery(false);
        verify(entity).updateDateTimeOrder(dateTime);   


        verify(pedidoRepository).save(entity);
        verify(pedidoMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o pedido não for encontrado")
    void shouldThrowWhenPedidoNotFound() {
        Long id = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        Pedido domainPedido = Pedido.create(
                id,
                dateTime,
                false,
                10L,
                1L,
                dateTime
        );

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        PedidoDomainException ex = assertThrows(PedidoDomainException.class, () -> gateway.update(domainPedido));
        assertEquals("Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(pedidoRepository).findById(id);
        verifyNoMoreInteractions(pedidoRepository, restaurantRepository, userRepository, pedidoMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo restaurante não for encontrado")
    void shouldThrowWhenNewRestaurantNotFound() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);
        Pedido domainPedido = Pedido.create(
                id,
                dateTime,
                false,
                newRestaurantId,
                1L,
                dateTime
        );

        PedidoEntity entity = mock(PedidoEntity.class);
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);

        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.empty());

        PedidoDomainException ex = assertThrows(PedidoDomainException.class, () -> gateway.update(domainPedido));
        assertEquals("Restaurante com ID " + newRestaurantId + " não encontrado.", ex.getMessage());

        verify(pedidoRepository).findById(id);
        verify(restaurantRepository).findById(newRestaurantId);

        verifyNoMoreInteractions(pedidoRepository, restaurantRepository, pedidoMapper);
    }
}
