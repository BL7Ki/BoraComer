package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemCreateGateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefault;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefaultWithId;

class CreatePedidoItemUseCaseImplTest {

    private PedidoItemCreateGateway pedidoItemCreateGateway;
    private CreatePedidoItemUseCaseImpl createPedidoItemUseCase;

    @BeforeEach
    void setUp() {
        pedidoItemCreateGateway = Mockito.mock(PedidoItemCreateGateway.class);
        createPedidoItemUseCase = new CreatePedidoItemUseCaseImpl(pedidoItemCreateGateway);
    }

    @Test
    void execute_shouldReturnCreatedPedido() {
        // Arrange
        PedidoItem pedidoItemToSave = createDefault();

        PedidoItem savedPedidoItem = createDefaultWithId();

        when(pedidoItemCreateGateway.save(any(PedidoItem.class))).thenReturn(savedPedidoItem);

        // Act
        PedidoItem result = createPedidoItemUseCase.execute(pedidoItemToSave);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(pedidoItemToSave.getMenuItemId(), result.getMenuItemId());
        assertEquals(pedidoItemToSave.getPedidoId(), result.getPedidoId());
        assertEquals(pedidoItemToSave.getQuantity(), result.getQuantity());
        verify(pedidoItemCreateGateway, times(1)).save(pedidoItemToSave);
    }
}
