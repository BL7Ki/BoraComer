package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefaultWithId;

class UpdatePedidoItemUseCaseImplTest {

    private PedidoItemUpdateGateway pedidoItemUpdateGateway;
    private UpdatePedidoItemUseCaseImpl updatePedidoItemUseCase;

    @BeforeEach
    void setUp() {
        pedidoItemUpdateGateway = Mockito.mock(PedidoItemUpdateGateway.class);
        updatePedidoItemUseCase = new UpdatePedidoItemUseCaseImpl(pedidoItemUpdateGateway);
    }

    @Test
    void execute_shouldUpdateAndReturnPedidoItem() {
        // Arrange
        PedidoItem pedidoItemToUpdate = createDefaultWithId();

        when(pedidoItemUpdateGateway.update(pedidoItemToUpdate)).thenReturn(pedidoItemToUpdate);

        // Act
        PedidoItem updatedPedidoItem = updatePedidoItemUseCase.execute(pedidoItemToUpdate);

        // Assert
        assertThat(updatedPedidoItem).isNotNull();
        verify(pedidoItemUpdateGateway, times(1)).update(pedidoItemToUpdate);
    }
}
