package pos.java.bora_comer.core.usercase.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.gateway.pedido.PedidoSearchGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createDefaultWithId;

class SearchPedidoUseCaseImplTest {

    private PedidoSearchGateway pedidoSearchGateway;
    private SearchPedidoUseCaseImpl searchPedidoUseCase;

    @BeforeEach
    void setUp() {
        pedidoSearchGateway = Mockito.mock(PedidoSearchGateway.class);
        searchPedidoUseCase = new SearchPedidoUseCaseImpl(pedidoSearchGateway);
    }

    @Test
    void findById_shouldReturnPedido() {
        // Arrange
        Pedido mockPedido = createDefaultWithId();

        when(pedidoSearchGateway.findById(10L)).thenReturn(mockPedido);

        // Act
        Pedido result = searchPedidoUseCase.findById(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRestaurantId()).isEqualTo(1L);
        verify(pedidoSearchGateway, times(1)).findById(10L);
    }

    @Test
    void findAll_shouldReturnPageOfPedidos() {
        // Arrange
        int page = 0;
        int size = 2;

        Pedido pedido1 = createDefaultWithId();
        Pedido pedido2 = createDefaultWithId();

        Page<Pedido> mockPage = new PageImpl<>(List.of(pedido1, pedido2));
        when(pedidoSearchGateway.findAll(page, size)).thenReturn(mockPage);

        // Act
        Page<Pedido> resultPage = searchPedidoUseCase.findAll(page, size);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        verify(pedidoSearchGateway, times(1)).findAll(page, size);
    }
}
