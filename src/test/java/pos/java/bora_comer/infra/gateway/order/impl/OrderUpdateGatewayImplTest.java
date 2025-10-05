package pos.java.bora_comer.infra.gateway.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefault;

class OrderUpdateGatewayImplTest {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);

        gateway = new OrderUpdateGatewayImpl(orderRepository, orderMapper);
    }

    @Test
    @DisplayName("Deve mapear o domínio para entidade e salvar o pedido atualizado com sucesso")
    void shouldMapDomainToEntityAndSaveUpdatedOrder() {
        Order orderToUpdate = createDefault();

        OrderEntity entityToSave = mock(OrderEntity.class);
        OrderEntity savedEntity = mock(OrderEntity.class);
        Order expectedDomain = mock(Order.class);

        when(orderMapper.toEntity(orderToUpdate)).thenReturn(entityToSave);
        when(orderRepository.save(entityToSave)).thenReturn(savedEntity);
        when(orderMapper.toDomain(savedEntity)).thenReturn(expectedDomain);

        Order result = gateway.update(orderToUpdate);

        assertNotNull(result);
        assertSame(expectedDomain, result);

        verify(orderMapper, times(1)).toEntity(orderToUpdate);
        verify(orderRepository, times(1)).save(entityToSave);
        verify(orderMapper, times(1)).toDomain(savedEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção se o ID do pedido for nulo (regra de persistência)")
    void shouldThrowWhenOrderIdIsNull() {
        Order orderWithoutId = mock(Order.class);
        when(orderWithoutId.getId()).thenReturn(null);

        OrderDomainException ex = assertThrows(OrderDomainException.class, () -> gateway.update(orderWithoutId));
        assertEquals("O pedido deve ter um ID para ser atualizado.", ex.getMessage());

        verifyNoInteractions(orderRepository, orderMapper);
    }

    @Test
    @DisplayName("Deve encontrar e mapear OrderEntity para Order Domain")
    void shouldFindAndMapOrderEntityToDomain() {
        Long id = 1L;
        OrderEntity entity = mock(OrderEntity.class);
        Order expectedDomain = mock(Order.class);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderMapper.toDomain(entity)).thenReturn(expectedDomain);

        Optional<Order> result = gateway.findById(id);

        assertTrue(result.isPresent());
        assertSame(expectedDomain, result.get());

        verify(orderRepository, times(1)).findById(id);
        verify(orderMapper, times(1)).toDomain(entity);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando o pedido não for encontrado")
    void shouldReturnEmptyOptionalWhenNotFound() {
        Long id = 99L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Order> result = gateway.findById(id);

        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findById(id);
        verifyNoInteractions(orderMapper);
    }
}