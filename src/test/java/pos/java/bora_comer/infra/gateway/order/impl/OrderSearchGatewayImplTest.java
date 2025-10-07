package pos.java.bora_comer.infra.gateway.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderSearchGatewayImplTest {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderSearchGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);
        gateway = new OrderSearchGatewayImpl(orderRepository, orderMapper);
    }

    @Test
    @DisplayName("Achar um pedido por ID deve retornar o item quando encontrado")
    void findById_shouldReturnOrder_whenFound() {
        Long id = 10L;
        pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity entity = mock(pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity.class);
        when(entity.getId()).thenReturn(id);
        Order domain = mock(Order.class);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderMapper.toDomain(entity)).thenReturn(domain);

        Order result = gateway.findById(id);

        assertSame(domain, result);
        verify(orderRepository).findById(id);
        verify(orderMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Achar um pedido por ID deve lançar SummerNotFoundException quando não encontrado")
    void findById_shouldThrow_whenNotFound() {
        Long id = 1L;

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.findById(id));
        assertEquals("Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(orderRepository).findById(id);
        verifyNoInteractions(orderMapper);
    }

    @Test
    @DisplayName("achar todos os pedidos deve retornar uma lista de pedidos")
    void findAll_shouldReturnPagedRestaurants() {
        int page = 0;
        int size = 2;

        // Criando mocks das entidades reais
        pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity entity1 = mock(pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity.class);
        pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity entity2 = mock(pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity.class);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), pageRequest, 2);

        // Criando mocks dos domínios convertidos
        Order domain1 = mock(Order.class);
        Order domain2 = mock(Order.class);

        when(orderRepository.findAll(pageRequest)).thenReturn(entityPage);
        when(orderMapper.toDomain(entity1)).thenReturn(domain1);
        when(orderMapper.toDomain(entity2)).thenReturn(domain2);

        Page<Order> resultPage = gateway.findAll(page, size);

        assertEquals(2, resultPage.getContent().size());
        assertTrue(resultPage.getContent().containsAll(List.of(domain1, domain2)));

        verify(orderRepository).findAll(pageRequest);
        verify(orderMapper).toDomain(entity1);
        verify(orderMapper).toDomain(entity2);
    }

}
