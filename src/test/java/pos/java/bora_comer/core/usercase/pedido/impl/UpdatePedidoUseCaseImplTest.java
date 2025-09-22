package pos.java.bora_comer.core.usercase.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.gateway.pedido.PedidoUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createDefaultWithId;

class UpdatePedidoUseCaseImplTest {

    private PedidoUpdateGateway pedidoUpdateGateway;
    private UpdatePedidoUseCaseImpl updatePedidoUseCase;

    @BeforeEach
    void setUp() {
        pedidoUpdateGateway = Mockito.mock(PedidoUpdateGateway.class);
        updatePedidoUseCase = new UpdatePedidoUseCaseImpl(pedidoUpdateGateway);
    }

    @Test
    void execute_shouldUpdateAndReturnPedido() {
        // Arrange
        Pedido pedidoToUpdate = createDefaultWithId();

        when(pedidoUpdateGateway.update(pedidoToUpdate)).thenReturn(pedidoToUpdate);

        // Act
        Pedido updatedPedido = updatePedidoUseCase.execute(pedidoToUpdate);

        // Assert
        assertThat(updatedPedido).isNotNull();
        verify(pedidoUpdateGateway, times(1)).update(pedidoToUpdate);
    }
}
