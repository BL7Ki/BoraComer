package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PedidoItemUpdateGatewayImplTest {

    private PedidoItemRepository pedidoItemRepository;
    private PedidoItemMapper pedidoItemMapper;
    private PedidoRepository pedidoRepository;
    private MenuItemRepository menuItemRepository;
    private PedidoItemUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoItemRepository = mock(PedidoItemRepository.class);
        pedidoItemMapper = mock(PedidoItemMapper.class);
        pedidoRepository = mock(PedidoRepository.class);
        menuItemRepository = mock(MenuItemRepository.class);

        gateway = new PedidoItemUpdateGatewayImpl(pedidoItemRepository, pedidoItemMapper, pedidoRepository, menuItemRepository);
    }

    @Test
    @DisplayName("Deve atualizar um item de pedido com sucesso quando o pedido não for alterado")
    void shouldUpdatePedidoItemWhenRestaurantIdNotChanged() {
        Long id = 1L;
        Long pedidoId = 10L;
        Long menuItemId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        PedidoItem domainPedidoItem = PedidoItem.create(
                id,
                pedidoId,
                menuItemId,
                2,
                dateTime
        );

        PedidoItemEntity entity = mock(PedidoItemEntity.class);
        when(pedidoItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getPedidoId()).thenReturn(pedidoId);
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(mock()));

        PedidoItemEntity updatedEntity = mock(PedidoItemEntity.class);
        when(pedidoItemRepository.save(entity)).thenReturn(updatedEntity);

        PedidoItem expectedDomain = mock(PedidoItem.class);
        when(pedidoItemMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        PedidoItem result = gateway.update(domainPedidoItem);

        verify(pedidoRepository, never()).findById(anyLong());
        verify(entity, never()).updatePedidoId(anyLong());
        verify(entity).updateMenuItemId(menuItemId);
        verify(entity).updateQuantity(2);
               
         

        verify(pedidoItemRepository).save(entity);
        verify(pedidoItemMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve atualizar um item de pedido e alterar o pedido quando o ID do pedido for alterado")
    void shouldUpdatePedidoItemAndChangePedido() {
        Long id = 1L;
        Long oldPedidoId = 10L;
        Long newPedidoId = 20L;
        Long menuItemId = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        PedidoItem domainPedidoItem = PedidoItem.create(
                id,
                newPedidoId,
                menuItemId,
                2,
                dateTime
        );

        PedidoItemEntity entity = mock(PedidoItemEntity.class);
        when(pedidoItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getPedidoId()).thenReturn(oldPedidoId);

        when(pedidoRepository.findById(newPedidoId)).thenReturn(Optional.of(mock()));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(mock()));

        PedidoItemEntity updatedEntity = mock(PedidoItemEntity.class);
        when(pedidoItemRepository.save(entity)).thenReturn(updatedEntity);

        PedidoItem expectedDomain = mock(PedidoItem.class);
        when(pedidoItemMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        PedidoItem result = gateway.update(domainPedidoItem);

        verify(pedidoRepository).findById(newPedidoId);
        verify(entity).updatePedidoId(newPedidoId);
        verify(entity).updateMenuItemId(menuItemId);
        verify(entity).updateQuantity(2);
        verify(pedidoItemRepository).save(entity);
        verify(pedidoItemMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item de pedido não for encontrado")
    void shouldThrowWhenPedidoItemNotFound() {
        Long id = 1L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        PedidoItem domainPedidoItem = PedidoItem.create(
                id,
                1L,
                1L,
                2,
                dateTime
        );

        when(pedidoItemRepository.findById(id)).thenReturn(Optional.empty());   
        PedidoItemDomainException ex = assertThrows(PedidoItemDomainException.class, () -> gateway.update(domainPedidoItem));
        assertEquals("Item de Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(pedidoItemRepository).findById(id);
        verifyNoMoreInteractions(pedidoItemRepository, pedidoRepository, menuItemRepository, pedidoItemMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo pedido não for encontrado")
    void shouldThrowWhenNewPedidoNotFound() {
        Long id = 1L;
        Long oldPedidoId = 10L;
        Long newPedidoId = 20L;

        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);

        PedidoItem domainPedidoItem = PedidoItem.create(
                id,
                newPedidoId,
                1L,
                2,
                dateTime
        );

        PedidoItemEntity entity = mock(PedidoItemEntity.class);
        when(pedidoItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getPedidoId()).thenReturn(oldPedidoId);

        when(pedidoRepository.findById(newPedidoId)).thenReturn(Optional.empty());

        PedidoItemDomainException ex = assertThrows(PedidoItemDomainException.class, () -> gateway.update(domainPedidoItem));
        assertEquals("Pedido com ID " + newPedidoId + " não encontrado.", ex.getMessage());

        verify(pedidoItemRepository).findById(id);
        verify(pedidoRepository).findById(newPedidoId);

        verifyNoMoreInteractions(pedidoItemRepository, pedidoRepository, pedidoItemMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo item de menu não for encontrado")
    void shouldThrowWhenNewMenuItemNotFound() {
        Long id = 1L;
        Long oldMenuItemId = 10L;
        Long newMenuItemId = 20L;
        String dateStr = "2024-10-10T12:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);
        PedidoItem domainPedidoItem = PedidoItem.create(
                id,
                1L,
                newMenuItemId,
                2,
                dateTime
        );

        PedidoItemEntity entity = mock(PedidoItemEntity.class);
        when(pedidoItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getMenuItemId()).thenReturn(oldMenuItemId);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(mock()));
        when(menuItemRepository.findById(newMenuItemId)).thenReturn(Optional.empty());

        PedidoItemDomainException ex = assertThrows(PedidoItemDomainException.class, () -> gateway.update(domainPedidoItem));
        assertEquals("Item de Menu com ID " + newMenuItemId + " não encontrado.", ex.getMessage());

        verify(pedidoItemRepository).findById(id);
        verify(menuItemRepository).findById(newMenuItemId);
        

        verifyNoMoreInteractions(pedidoItemRepository, menuItemRepository, pedidoItemMapper);
    }
}
