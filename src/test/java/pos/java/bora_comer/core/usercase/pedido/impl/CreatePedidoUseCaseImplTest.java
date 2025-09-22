package pos.java.bora_comer.core.usercase.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.gateway.pedido.PedidoCreateGateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createDefault;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createDefaultWithId;

class CreatePedidoUseCaseImplTest {

    private PedidoCreateGateway pedidoCreateGateway;
    private CreatePedidoUseCaseImpl createPedidoUseCase;

    @BeforeEach
    void setUp() {
        pedidoCreateGateway = Mockito.mock(PedidoCreateGateway.class);
        createPedidoUseCase = new CreatePedidoUseCaseImpl(pedidoCreateGateway);
    }

    @Test
    void execute_shouldReturnCreatedPedido() {
        // Arrange
        Pedido pedidoToSave = createDefault();

        Pedido savedPedido = createDefaultWithId();

        when(pedidoCreateGateway.save(any(Pedido.class))).thenReturn(savedPedido);

        // Act
        Pedido result = createPedidoUseCase.execute(pedidoToSave);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(pedidoToSave.getUserId(), result.getUserId());
        assertEquals(pedidoToSave.getRestaurantId(), result.getRestaurantId());
        assertEquals(pedidoToSave.isDelivery(), result.isDelivery());
        verify(pedidoCreateGateway, times(1)).save(pedidoToSave);
    }
}
