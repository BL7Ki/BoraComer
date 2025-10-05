package pos.java.bora_comer.infra.gateway.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;
import pos.java.bora_comer.infra.rabbitmq.OrderEventProducer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefault;

class OrderCreateGatewayImplTest {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderEventProducer orderEventProducer;
    private OrderCreateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);
        orderEventProducer = mock(OrderEventProducer.class);

        gateway = new OrderCreateGatewayImpl(orderRepository, orderMapper, orderEventProducer);
    }

    @Test
    @DisplayName("Deve salvar o pedido, retornar o domínio e enviar o evento")
    void shouldSaveAndReturnOrderAndSendEvent() {
        Order order = createDefault();
        Order domainFromSaved = createDefault();
        OrderEntity entityToSave = new OrderEntity();
        OrderEntity savedEntity = new OrderEntity();

        when(orderMapper.toEntity(order)).thenReturn(entityToSave);
        when(orderRepository.save(entityToSave)).thenReturn(savedEntity);
        when(orderMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        Order result = gateway.save(order);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());

        verify(orderMapper, times(1)).toEntity(order);
        verify(orderRepository, times(1)).save(entityToSave);
        verify(orderMapper, times(1)).toDomain(savedEntity);

        verify(orderEventProducer, times(1)).sendOrderCreatedEvent(domainFromSaved);
    }
}