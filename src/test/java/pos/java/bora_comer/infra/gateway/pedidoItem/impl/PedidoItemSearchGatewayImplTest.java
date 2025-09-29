package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoItemSearchGatewayImplTest {

    private PedidoItemRepository pedidoItemRepository;
    private PedidoItemMapper pedidoItemMapper;
    private PedidoItemSearchGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoItemRepository = mock(PedidoItemRepository.class);
        pedidoItemMapper = mock(PedidoItemMapper.class);
        gateway = new PedidoItemSearchGatewayImpl(pedidoItemRepository, pedidoItemMapper);
    }

    @Test
    @DisplayName("Achar um item de pedido por ID deve retornar o item quando encontrado")
    void findById_shouldReturnPedidoItem_whenFound() {
        Long id = 10L;
        pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity entity = mock(pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity.class);
        when(entity.getId()).thenReturn(id);
        PedidoItem domain = mock(PedidoItem.class);

        when(pedidoItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(pedidoItemMapper.toDomain(entity)).thenReturn(domain);

        PedidoItem result = gateway.findById(id);

        assertSame(domain, result);
        verify(pedidoItemRepository).findById(id);
        verify(pedidoItemMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Achar um item de pedido por ID deve lançar SummerNotFoundException quando não encontrado")
    void findById_shouldThrow_whenNotFound() {
        Long id = 1L;

        when(pedidoItemRepository.findById(id)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.findById(id));
        assertEquals("Item de Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(pedidoItemRepository).findById(id);
        verifyNoInteractions(pedidoItemMapper);
    }

    @Test
    @DisplayName("achar todos os itens de pedidos deve retornar uma lista de itens")
    void findAll_shouldReturnPagedRestaurants() {
        int page = 0;
        int size = 2;

        // Criando mocks das entidades reais
        pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity entity1 = mock(pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity.class);
        pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity entity2 = mock(pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity.class);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), pageRequest, 2);

        // Criando mocks dos domínios convertidos
        PedidoItem domain1 = mock(PedidoItem.class);
        PedidoItem domain2 = mock(PedidoItem.class);

        when(pedidoItemRepository.findAll(pageRequest)).thenReturn(entityPage);
        when(pedidoItemMapper.toDomain(entity1)).thenReturn(domain1);
        when(pedidoItemMapper.toDomain(entity2)).thenReturn(domain2);

        Page<PedidoItem> resultPage = gateway.findAll(page, size);

        assertEquals(2, resultPage.getContent().size());
        assertTrue(resultPage.getContent().containsAll(List.of(domain1, domain2)));

        verify(pedidoItemRepository).findAll(pageRequest);
        verify(pedidoItemMapper).toDomain(entity1);
        verify(pedidoItemMapper).toDomain(entity2);
    }

}
