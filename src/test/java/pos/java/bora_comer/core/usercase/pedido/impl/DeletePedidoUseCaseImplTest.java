package pos.java.bora_comer.core.usercase.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.gateway.pedido.PedidoDeleteGateway;

import static org.mockito.Mockito.*;

class DeletePedidoUseCaseImplTest {

    private PedidoDeleteGateway pedidoDeleteGateway;
    private DeletePedidoUseCaseImpl deletePedidoUseCase;

    @BeforeEach
    void setUp() {
        pedidoDeleteGateway = Mockito.mock(PedidoDeleteGateway.class);
        deletePedidoUseCase = new DeletePedidoUseCaseImpl(pedidoDeleteGateway);
    }

    @Test
    void execute_shouldCallDeleteByIdOnce() {
        // Arrange
        Long pedidoId = 5L;

        // Act
        deletePedidoUseCase.execute(pedidoId);

        // Assert
        verify(pedidoDeleteGateway, times(1)).deleteById(pedidoId);
    }
}
