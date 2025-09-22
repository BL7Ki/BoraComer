package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoSearchGatewayImplTest {

    private PedidoRepository pedidoRepository;
    private PedidoMapper pedidoMapper;
    private PedidoSearchGatewayImpl gateway;

    @BeforeEach
    void setup() {
        pedidoRepository = mock(PedidoRepository.class);
        pedidoMapper = mock(PedidoMapper.class);
        gateway = new PedidoSearchGatewayImpl(pedidoRepository, pedidoMapper);
    }

    @Test
    @DisplayName("Achar um pedido por ID deve retornar o item quando encontrado")
    void findById_shouldReturnPedido_whenFound() {
        Long id = 10L;
        pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity entity = mock(pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity.class);
        when(entity.getId()).thenReturn(id);
        Pedido domain = mock(Pedido.class);

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(pedidoMapper.toDomain(entity)).thenReturn(domain);

        Pedido result = gateway.findById(id);

        assertSame(domain, result);
        verify(pedidoRepository).findById(id);
        verify(pedidoMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Achar um pedido por ID deve lançar SummerNotFoundException quando não encontrado")
    void findById_shouldThrow_whenNotFound() {
        Long id = 1L;

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.findById(id));
        assertEquals("Pedido com ID " + id + " não encontrado.", ex.getMessage());

        verify(pedidoRepository).findById(id);
        verifyNoInteractions(pedidoMapper);
    }

    @Test
    @DisplayName("achar todos os pedidos deve retornar uma lista de itens")
    void findAll_shouldReturnPagedRestaurants() {
        int page = 0;
        int size = 2;

        // Criando mocks das entidades reais
        pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity entity1 = mock(pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity.class);
        pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity entity2 = mock(pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity.class);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), pageRequest, 2);

        // Criando mocks dos domínios convertidos
        Pedido domain1 = mock(Pedido.class);
        Pedido domain2 = mock(Pedido.class);

        when(pedidoRepository.findAll(pageRequest)).thenReturn(entityPage);
        when(pedidoMapper.toDomain(entity1)).thenReturn(domain1);
        when(pedidoMapper.toDomain(entity2)).thenReturn(domain2);

        Page<Pedido> resultPage = gateway.findAll(page, size);

        assertEquals(2, resultPage.getContent().size());
        assertTrue(resultPage.getContent().containsAll(List.of(domain1, domain2)));

        verify(pedidoRepository).findAll(pageRequest);
        verify(pedidoMapper).toDomain(entity1);
        verify(pedidoMapper).toDomain(entity2);
    }

}
