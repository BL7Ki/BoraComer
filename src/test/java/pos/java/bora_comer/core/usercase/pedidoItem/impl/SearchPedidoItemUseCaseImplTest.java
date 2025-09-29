package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemSearchGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefaultWithId;

class SearchPedidoItemUseCaseImplTest {

    private PedidoItemSearchGateway pedidoItemSearchGateway;
    private SearchPedidoItemUseCaseImpl searchPedidoItemUseCase;

    @BeforeEach
    void setUp() {
        pedidoItemSearchGateway = Mockito.mock(PedidoItemSearchGateway.class);
        searchPedidoItemUseCase = new SearchPedidoItemUseCaseImpl(pedidoItemSearchGateway);
    }

    @Test
    void findById_shouldReturnPedidoItem() {
        // Arrange
        PedidoItem mockPedidoItem = createDefaultWithId();

        when(pedidoItemSearchGateway.findById(10L)).thenReturn(mockPedidoItem);

        // Act
        PedidoItem result = searchPedidoItemUseCase.findById(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getMenuItemId()).isEqualTo(1L);
        assertThat(result.getPedidoId()).isEqualTo(1L);
        verify(pedidoItemSearchGateway, times(1)).findById(10L);
    }

    @Test
    void findAll_shouldReturnPageOfPedidoItems() {
        // Arrange
        int page = 0;
        int size = 2;

        PedidoItem pedidoItem1 = createDefaultWithId();
        PedidoItem pedidoItem2 = createDefaultWithId();

        Page<PedidoItem> mockPage = new PageImpl<>(List.of(pedidoItem1, pedidoItem2));
        when(pedidoItemSearchGateway.findAll(page, size)).thenReturn(mockPage);

        // Act
        Page<PedidoItem> resultPage = searchPedidoItemUseCase.findAll(page, size);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        verify(pedidoItemSearchGateway, times(1)).findAll(page, size);
    }
}
