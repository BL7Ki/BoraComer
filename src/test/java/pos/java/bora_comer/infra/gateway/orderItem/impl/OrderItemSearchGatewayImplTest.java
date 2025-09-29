package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemSearchGatewayImplTest {

    private OrderItemRepository orderItemRepository;
    private OrderItemMapper orderItemMapper;
    private OrderItemSearchGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderItemRepository = mock(OrderItemRepository.class);
        orderItemMapper = mock(OrderItemMapper.class);
        gateway = new OrderItemSearchGatewayImpl(orderItemRepository, orderItemMapper);
    }

    @Test
    @DisplayName("Achar um item de pedido por ID deve retornar o item quando encontrado")
    void findById_shouldReturnOrderItem_whenFound() {
        Long id = 10L;
        pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity entity = mock(pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity.class);
        when(entity.getId()).thenReturn(id);
        OrderItem domain = mock(OrderItem.class);

        when(orderItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderItemMapper.toDomain(entity)).thenReturn(domain);

        OrderItem result = gateway.findById(id);

        assertSame(domain, result);
        verify(orderItemRepository).findById(id);
        verify(orderItemMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Achar um item de pedido por ID deve lançar SummerNotFoundException quando não encontrado")
    void findById_shouldThrow_whenNotFound() {
        Long id = 1L;

        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.findById(id));
        assertEquals("Item de Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(orderItemRepository).findById(id);
        verifyNoInteractions(orderItemMapper);
    }

    @Test
    @DisplayName("achar todos os itens de pedidos deve retornar uma lista de itens")
    void findAll_shouldReturnPagedRestaurants() {
        int page = 0;
        int size = 2;

        // Criando mocks das entidades reais
        pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity entity1 = mock(pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity.class);
        pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity entity2 = mock(pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity.class);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), pageRequest, 2);

        // Criando mocks dos domínios convertidos
        OrderItem domain1 = mock(OrderItem.class);
        OrderItem domain2 = mock(OrderItem.class);

        when(orderItemRepository.findAll(pageRequest)).thenReturn(entityPage);
        when(orderItemMapper.toDomain(entity1)).thenReturn(domain1);
        when(orderItemMapper.toDomain(entity2)).thenReturn(domain2);

        Page<OrderItem> resultPage = gateway.findAll(page, size);

        assertEquals(2, resultPage.getContent().size());
        assertTrue(resultPage.getContent().containsAll(List.of(domain1, domain2)));

        verify(orderItemRepository).findAll(pageRequest);
        verify(orderItemMapper).toDomain(entity1);
        verify(orderItemMapper).toDomain(entity2);
    }

}
