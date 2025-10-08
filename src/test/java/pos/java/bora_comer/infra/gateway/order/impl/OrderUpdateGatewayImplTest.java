package pos.java.bora_comer.infra.gateway.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.core.domain.order.OrderStatus;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderUpdateGatewayImplTest {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private OrderUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);
        restaurantRepository = mock(RestaurantRepository.class);
        userRepository = mock(UserRepository.class);
        gateway = new OrderUpdateGatewayImpl(orderRepository, orderMapper, restaurantRepository, userRepository);
    }

    @Test
    @DisplayName("Deve atualizar um pedido com sucesso quando o restaurante e usuário não forem alterados")
    void shouldUpdateOrderWhenIdsNotChanged() {
        Long id = 1L;
        Long restaurantId = 10L;
        Long userId = 1L;
        LocalDateTime dateTime = LocalDateTime.parse("2024-10-10T12:00:00");

        Order domainOrder = Order.create(id, dateTime, false, restaurantId, userId, dateTime, OrderStatus.PENDING);
        OrderEntity entity = mock(OrderEntity.class);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(restaurantId);
        when(entity.getUserId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(UserEntity.class)));

        OrderEntity updatedEntity = mock(OrderEntity.class);
        when(orderRepository.save(entity)).thenReturn(updatedEntity);

        Order expectedDomain = mock(Order.class);
        when(orderMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Order result = gateway.update(domainOrder);

        verify(restaurantRepository, never()).findById(anyLong());
        verify(entity, never()).updateRestaurantId(anyLong());
        verify(entity, never()).updateUserId(anyLong());
        verify(entity, times(2)).updateDateTimeOrder(dateTime);
        verify(entity).updateDelivery(false);
        verify(entity).updateLastModifiedDate();
        verify(orderRepository).save(entity);
        verify(orderMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve atualizar um pedido e alterar o restaurante quando o ID for diferente")
    void shouldUpdateOrderAndChangeRestaurant() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;
        Long userId = 1L;
        LocalDateTime dateTime = LocalDateTime.parse("2024-10-10T12:00:00");

        Order domainOrder = Order.create(id, dateTime, false, newRestaurantId, userId, dateTime, OrderStatus.PENDING);
        OrderEntity entity = mock(OrderEntity.class);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);
        when(entity.getUserId()).thenReturn(userId);
        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.of(mock(RestaurantEntity.class)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(UserEntity.class)));


        OrderEntity updatedEntity = mock(OrderEntity.class);
        when(orderRepository.save(entity)).thenReturn(updatedEntity);
        Order expectedDomain = mock(Order.class);
        when(orderMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        Order result = gateway.update(domainOrder);

        verify(restaurantRepository).findById(newRestaurantId);
        verify(entity).updateRestaurantId(newRestaurantId);
        verify(entity, never()).updateUserId(anyLong());
        verify(entity).updateDelivery(false);
        verify(entity).updateLastModifiedDate();
        verify(entity, times(2)).updateDateTimeOrder(dateTime);
        verify(orderRepository).save(entity);
        verify(orderMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o pedido não for encontrado")
    void shouldThrowWhenOrderNotFound() {
        Long id = 1L;
        LocalDateTime dateTime = LocalDateTime.parse("2024-10-10T12:00:00");

        Order domainOrder = Order.create(id, dateTime, false, 10L, 1L, dateTime, OrderStatus.PENDING);

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        OrderDomainException ex = assertThrows(OrderDomainException.class, () -> gateway.update(domainOrder));
        assertEquals("Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(orderRepository).findById(id);
        verifyNoMoreInteractions(orderRepository, restaurantRepository, userRepository, orderMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo restaurante não for encontrado")
    void shouldThrowWhenNewRestaurantNotFound() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;
        LocalDateTime dateTime = LocalDateTime.parse("2024-10-10T12:00:00");

        Order domainOrder = Order.create(id, dateTime, false, newRestaurantId, 1L, dateTime, OrderStatus.PENDING);
        OrderEntity entity = mock(OrderEntity.class);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);
        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.empty());

        OrderDomainException ex = assertThrows(OrderDomainException.class, () -> gateway.update(domainOrder));
        assertEquals("Restaurante com ID " + newRestaurantId + " não encontrado.", ex.getMessage());

        verify(orderRepository).findById(id);
        verify(restaurantRepository).findById(newRestaurantId);
        verifyNoMoreInteractions(orderRepository, restaurantRepository, orderMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo usuário não for encontrado")
    void shouldThrowWhenNewUserNotFound() {
        Long id = 1L;
        Long oldUserId = 10L;
        Long newRestaurantId = 20L;
        Long newUserId = 20L;
        LocalDateTime dateTime = LocalDateTime.parse("2024-10-10T12:00:00");

        Order domainOrder = Order.create(id, dateTime, false, 1L, newUserId, dateTime, OrderStatus.PENDING);
        OrderEntity entity = mock(OrderEntity.class);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getUserId()).thenReturn(oldUserId);
        when(entity.getRestaurantId()).thenReturn(1L);
        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.of(mock(RestaurantEntity.class)));
        when(userRepository.findById(newUserId)).thenReturn(Optional.empty());

        OrderDomainException ex = assertThrows(OrderDomainException.class, () -> gateway.update(domainOrder));
        assertEquals("Usuário com ID " + newUserId + " não encontrado.", ex.getMessage());

        verify(orderRepository).findById(id);
        verify(userRepository).findById(newUserId);
        verifyNoMoreInteractions(orderRepository, userRepository, orderMapper);
    }
}
