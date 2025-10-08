package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;
import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefault;

class OrderItemCreateGatewayImplTest {

    private OrderItemRepository orderItemRepository;
    private OrderItemMapper orderItemMapper;
    private OrderItemCreateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderItemRepository = mock(OrderItemRepository.class);
        orderItemMapper = mock(OrderItemMapper.class);
        gateway = new OrderItemCreateGatewayImpl(orderItemRepository, orderItemMapper);
    }

    @Test
    @DisplayName("Deve salvar e retornar o item de pedido")
    void shouldSaveAndReturnOrder() {
        OrderItem orderItem = createDefault();
        OrderItemEntity entityToSave = new OrderItemEntity();
        OrderItemEntity savedEntity = new OrderItemEntity();
        OrderItem domainFromSaved = createDefault();

        when(orderItemMapper.toEntity(orderItem)).thenReturn(entityToSave);
        when(orderItemRepository.save(entityToSave)).thenReturn(savedEntity);
        when(orderItemMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        OrderItem result = gateway.save(orderItem);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());
        assertEquals(domainFromSaved.getMenuItemId(), result.getMenuItemId());
        assertEquals(domainFromSaved.getOrderId(), result.getOrderId());
                
        
        verify(orderItemMapper, times(1)).toEntity(orderItem);
        verify(orderItemRepository, times(1)).save(entityToSave);
        verify(orderItemMapper, times(1)).toDomain(savedEntity);
    }
}
