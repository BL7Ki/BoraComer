package pos.java.bora_comer.infra.gateway.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefault;

class OrderCreateGatewayImplTest {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderCreateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);
        gateway = new OrderCreateGatewayImpl(orderRepository, orderMapper);
    }

    @Test
    @DisplayName("Deve salvar e retornar o pedido")
    void shouldSaveAndReturnOrder() {
        Order order = createDefault();
        OrderEntity entityToSave = new OrderEntity();
        OrderEntity savedEntity = new OrderEntity();
        Order domainFromSaved = createDefault();

        when(orderMapper.toEntity(order)).thenReturn(entityToSave);
        when(orderRepository.save(entityToSave)).thenReturn(savedEntity);
        when(orderMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        Order result = gateway.save(order);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());
        assertEquals(domainFromSaved.getUserId(), result.getUserId());
        assertEquals(domainFromSaved.getRestaurantId(), result.getRestaurantId());
                
        
        verify(orderMapper, times(1)).toEntity(order);
        verify(orderRepository, times(1)).save(entityToSave);
        verify(orderMapper, times(1)).toDomain(savedEntity);
    }
}
