package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createDefault;

class PedidoCreateGatewayImplTest {

    private PedidoRepository pedidoRepository;
    private PedidoMapper pedidoMapper;
    private PedidoCreateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoRepository = mock(PedidoRepository.class);
        pedidoMapper = mock(PedidoMapper.class);
        gateway = new PedidoCreateGatewayImpl(pedidoRepository, pedidoMapper);
    }

    @Test
    @DisplayName("Deve salvar e retornar o pedido")
    void shouldSaveAndReturnPedido() {
        Pedido pedido = createDefault();
        PedidoEntity entityToSave = new PedidoEntity();
        PedidoEntity savedEntity = new PedidoEntity();
        Pedido domainFromSaved = createDefault();

        when(pedidoMapper.toEntity(pedido)).thenReturn(entityToSave);
        when(pedidoRepository.save(entityToSave)).thenReturn(savedEntity);
        when(pedidoMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        Pedido result = gateway.save(pedido);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());
        assertEquals(domainFromSaved.getUserId(), result.getUserId());
        assertEquals(domainFromSaved.getRestaurantId(), result.getRestaurantId());
                
        
        verify(pedidoMapper, times(1)).toEntity(pedido);
        verify(pedidoRepository, times(1)).save(entityToSave);
        verify(pedidoMapper, times(1)).toDomain(savedEntity);
    }
}
