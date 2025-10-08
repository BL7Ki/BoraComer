package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;
import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderItemUpdateGatewayImplTest {

    private OrderItemRepository orderItemRepository;
    private OrderItemMapper orderItemMapper;
    private OrderRepository orderRepository;
    private MenuItemRepository menuItemRepository;
    private OrderItemUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        orderItemRepository = mock(OrderItemRepository.class);
        orderItemMapper = mock(OrderItemMapper.class);
        orderRepository = mock(OrderRepository.class);
        menuItemRepository = mock(MenuItemRepository.class);

        gateway = new OrderItemUpdateGatewayImpl(orderItemRepository, orderItemMapper, orderRepository, menuItemRepository);
    }

    @Test
    @DisplayName("Deve atualizar um item de pedido com sucesso quando o pedido não for alterado")
    void shouldUpdateOrderItemWhenRestaurantIdNotChanged() {
        Long id = 1L;
        Long orderId = 10L;
        Long menuItemId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        OrderItem domainOrderItem = OrderItem.create(
                id,
                orderId,
                menuItemId,
                2,
                dateTime
        );

        OrderItemEntity entity = mock(OrderItemEntity.class);
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getOrderId()).thenReturn(orderId);
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(mock()));

        OrderItemEntity updatedEntity = mock(OrderItemEntity.class);
        when(orderItemRepository.save(entity)).thenReturn(updatedEntity);

        OrderItem expectedDomain = mock(OrderItem.class);
        when(orderItemMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        OrderItem result = gateway.update(domainOrderItem);

        verify(orderRepository, never()).findById(anyLong());
        verify(entity, never()).updateOrderId(anyLong());
        verify(entity).updateMenuItemId(menuItemId);
        verify(entity).updateQuantity(2);
               
         

        verify(orderItemRepository).save(entity);
        verify(orderItemMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve atualizar um item de pedido e alterar o pedido quando o ID do pedido for alterado")
    void shouldUpdateOrderItemAndChangeOrder() {
        Long id = 1L;
        Long oldOrderId = 10L;
        Long newOrderId = 20L;
        Long menuItemId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        OrderItem domainOrderItem = OrderItem.create(
                id,
                newOrderId,
                menuItemId,
                2,
                dateTime
        );

        OrderItemEntity entity = mock(OrderItemEntity.class);
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getOrderId()).thenReturn(oldOrderId);

        when(orderRepository.findById(newOrderId)).thenReturn(Optional.of(mock()));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(mock()));

        OrderItemEntity updatedEntity = mock(OrderItemEntity.class);
        when(orderItemRepository.save(entity)).thenReturn(updatedEntity);

        OrderItem expectedDomain = mock(OrderItem.class);
        when(orderItemMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        OrderItem result = gateway.update(domainOrderItem);

        verify(orderRepository).findById(newOrderId);
        verify(entity).updateOrderId(newOrderId);
        verify(entity).updateMenuItemId(menuItemId);
        verify(entity).updateQuantity(2);
        verify(orderItemRepository).save(entity);
        verify(orderItemMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item de pedido não for encontrado")
    void shouldThrowWhenOrderItemNotFound() {
        Long id = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        OrderItem domainOrderItem = OrderItem.create(
                id,
                1L,
                1L,
                2,
                dateTime
        );

        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());   
        OrderItemDomainException ex = assertThrows(OrderItemDomainException.class, () -> gateway.update(domainOrderItem));
        assertEquals("Item de Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(orderItemRepository).findById(id);
        verifyNoMoreInteractions(orderItemRepository, orderRepository, menuItemRepository, orderItemMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo pedido não for encontrado")
    void shouldThrowWhenNewOrderNotFound() {
        Long id = 1L;
        Long oldOrderId = 10L;
        Long newOrderId = 20L;

        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        OrderItem domainOrderItem = OrderItem.create(
                id,
                newOrderId,
                1L,
                2,
                dateTime
        );

        OrderItemEntity entity = mock(OrderItemEntity.class);
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getOrderId()).thenReturn(oldOrderId);

        when(orderRepository.findById(newOrderId)).thenReturn(Optional.empty());

        OrderItemDomainException ex = assertThrows(OrderItemDomainException.class, () -> gateway.update(domainOrderItem));
        assertEquals("Pedido com ID " + newOrderId + " não encontrado.", ex.getMessage());

        verify(orderItemRepository).findById(id);
        verify(orderRepository).findById(newOrderId);

        verifyNoMoreInteractions(orderItemRepository, orderRepository, orderItemMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo item de menu não for encontrado")
    void shouldThrowWhenNewMenuItemNotFound() {
        Long id = 1L;
        Long oldMenuItemId = 10L;
        Long newMenuItemId = 20L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);
        OrderItem domainOrderItem = OrderItem.create(
                id,
                1L,
                newMenuItemId,
                2,
                dateTime
        );

        OrderItemEntity entity = mock(OrderItemEntity.class);
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getMenuItemId()).thenReturn(oldMenuItemId);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mock()));
        when(menuItemRepository.findById(newMenuItemId)).thenReturn(Optional.empty());

        OrderItemDomainException ex = assertThrows(OrderItemDomainException.class, () -> gateway.update(domainOrderItem));
        assertEquals("Item de Menu com ID " + newMenuItemId + " não encontrado.", ex.getMessage());

        verify(orderItemRepository).findById(id);
        verify(menuItemRepository).findById(newMenuItemId);
        

        verifyNoMoreInteractions(orderItemRepository, menuItemRepository, orderItemMapper);
    }
}
