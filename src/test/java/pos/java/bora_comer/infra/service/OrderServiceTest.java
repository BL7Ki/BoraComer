package pos.java.bora_comer.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.rabbitmq.OrderEventProducer;
import pos.java.bora_comer.util.factory.OrderTestFactory;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderEventProducer orderEventProducer;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = OrderTestFactory.createDefaultWithId();
    }

    @Test
    void execute_shouldReturnThePassedOrder() {
        Order returnedOrder = orderService.execute(testOrder);

        // Verificação: O objeto retornado deve ser o mesmo que foi passado (ou a versão persistida)
        assertNotNull(returnedOrder, "A Order retornada não deve ser nula.");
        assertEquals(testOrder, returnedOrder, "O objeto retornado deve ser o mesmo que o de entrada.");
        assertEquals(testOrder.getId(), returnedOrder.getId(), "O ID da Order deve ser mantido.");
    }

    @Test
    void execute_shouldSendOrderCreatedEventOnce() {
        orderService.execute(testOrder);

        // Verificação: Garante que o método de envio de evento foi chamado
        verify(orderEventProducer, times(1)).sendOrderCreatedEvent(testOrder);

        verifyNoMoreInteractions(orderEventProducer);
    }

    @Test
    void execute_shouldHandleOrderWithoutIdAndReturnIt() {
        Order orderWithoutId = OrderTestFactory.createDefault();

        Order returnedOrder = orderService.execute(orderWithoutId);

        // Verificação 1: A Order foi retornada
        assertNotNull(returnedOrder);

        // Verificação 2: O evento foi enviado com a Order sem ID
        verify(orderEventProducer, times(1)).sendOrderCreatedEvent(orderWithoutId);

        // Verificação 3: O ID continua nulo, pois o serviço não simula persistência aqui
        assertEquals(null, returnedOrder.getId(), "O ID deve ser nulo se não houver persistência no serviço.");
    }
}