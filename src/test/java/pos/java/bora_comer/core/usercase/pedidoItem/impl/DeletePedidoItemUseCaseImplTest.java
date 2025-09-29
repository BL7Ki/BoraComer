package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemDeleteGateway;

import static org.mockito.Mockito.*;

class DeletePedidoItemUseCaseImplTest {

    private PedidoItemDeleteGateway pedidoItemDeleteGateway;
    private DeletePedidoItemUseCaseImpl deletePedidoItemUseCase;

    @BeforeEach
    void setUp() {
        pedidoItemDeleteGateway = Mockito.mock(PedidoItemDeleteGateway.class);
        deletePedidoItemUseCase = new DeletePedidoItemUseCaseImpl(pedidoItemDeleteGateway);
    }

    @Test
    void execute_shouldCallDeleteByIdOnce() {
        // Arrange
        Long pedidoItemId = 5L;

        // Act
        deletePedidoItemUseCase.execute(pedidoItemId);

        // Assert
        verify(pedidoItemDeleteGateway, times(1)).deleteById(pedidoItemId);
    }
}
