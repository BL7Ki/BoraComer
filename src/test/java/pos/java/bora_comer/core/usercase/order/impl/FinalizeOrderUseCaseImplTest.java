package pos.java.bora_comer.core.usercase.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.gateway.order.OrderUpdateGateway;
import pos.java.bora_comer.core.usercase.order.FinalizeOrderUseCase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;

class FinalizeOrderUseCaseImplTest {

    private OrderUpdateGateway orderUpdateGateway;
    private FinalizeOrderUseCase finalizeOrderUseCase;

    private static final Long EXISTING_ORDER_ID = 1L;
    private static final Long NON_EXISTENT_ORDER_ID = 99L;

    @BeforeEach
    void setUp() {
        orderUpdateGateway = mock(OrderUpdateGateway.class);
        finalizeOrderUseCase = new FinalizeOrderUseCaseImpl(orderUpdateGateway);
    }

    @Test
    @DisplayName("Deve finalizar um pedido existente, atualizar o status e salvar")
    void shouldFinalizeExistingOrderAndUpdateStatus() {
        // Arrange
        Order orderBeforeUpdateMock = mock(Order.class);

        Order expectedFinalizedOrder = createDefaultWithId(EXISTING_ORDER_ID, OrderStatusEnum.FINALIZED);

        when(orderBeforeUpdateMock.getId()).thenReturn(EXISTING_ORDER_ID);

        when(orderUpdateGateway.findById(EXISTING_ORDER_ID)).thenReturn(Optional.of(orderBeforeUpdateMock));

        when(orderBeforeUpdateMock.updateStatus(OrderStatusEnum.FINALIZED)).thenReturn(expectedFinalizedOrder);

        when(orderUpdateGateway.update(expectedFinalizedOrder)).thenReturn(expectedFinalizedOrder);

        // Act
        Order result = finalizeOrderUseCase.execute(EXISTING_ORDER_ID);

        // Assert
        assertNotNull(result);
        assertEquals(EXISTING_ORDER_ID, result.getId());
        assertEquals(OrderStatusEnum.FINALIZED, result.getStatus());
        assertSame(expectedFinalizedOrder, result);

        verify(orderUpdateGateway, times(1)).findById(EXISTING_ORDER_ID);
        verify(orderBeforeUpdateMock, times(1)).updateStatus(OrderStatusEnum.FINALIZED);
        verify(orderUpdateGateway, times(1)).update(expectedFinalizedOrder);
    }

    @Test
    @DisplayName("Deve lançar OrderDomainException se o pedido não for encontrado")
    void shouldThrowExceptionWhenOrderNotFound() {
        // Arrange
        when(orderUpdateGateway.findById(NON_EXISTENT_ORDER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        OrderDomainException ex = assertThrows(OrderDomainException.class,
                () -> finalizeOrderUseCase.execute(NON_EXISTENT_ORDER_ID));

        assertEquals("Pedido com ID " + NON_EXISTENT_ORDER_ID + " não encontrado para finalização.", ex.getMessage());

        // Verificações
        verify(orderUpdateGateway, times(1)).findById(NON_EXISTENT_ORDER_ID);
        verify(orderUpdateGateway, never()).update(any(Order.class));
    }
}