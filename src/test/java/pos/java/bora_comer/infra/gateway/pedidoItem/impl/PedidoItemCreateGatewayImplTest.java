package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefault;

class PedidoItemCreateGatewayImplTest {

    private PedidoItemRepository pedidoItemRepository;
    private PedidoItemMapper pedidoItemMapper;
    private PedidoItemCreateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoItemRepository = mock(PedidoItemRepository.class);
        pedidoItemMapper = mock(PedidoItemMapper.class);
        gateway = new PedidoItemCreateGatewayImpl(pedidoItemRepository, pedidoItemMapper);
    }

    @Test
    @DisplayName("Deve salvar e retornar o item de pedido")
    void shouldSaveAndReturnPedido() {
        PedidoItem pedidoItem = createDefault();
        PedidoItemEntity entityToSave = new PedidoItemEntity();
        PedidoItemEntity savedEntity = new PedidoItemEntity();
        PedidoItem domainFromSaved = createDefault();

        when(pedidoItemMapper.toEntity(pedidoItem)).thenReturn(entityToSave);
        when(pedidoItemRepository.save(entityToSave)).thenReturn(savedEntity);
        when(pedidoItemMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        PedidoItem result = gateway.save(pedidoItem);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());
        assertEquals(domainFromSaved.getMenuItemId(), result.getMenuItemId());
        assertEquals(domainFromSaved.getPedidoId(), result.getPedidoId());
                
        
        verify(pedidoItemMapper, times(1)).toEntity(pedidoItem);
        verify(pedidoItemRepository, times(1)).save(entityToSave);
        verify(pedidoItemMapper, times(1)).toDomain(savedEntity);
    }
}
