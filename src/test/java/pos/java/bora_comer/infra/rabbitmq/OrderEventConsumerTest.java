package pos.java.bora_comer.infra.rabbitmq;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.java.bora_comer.core.port.NotificationService;

@ExtendWith(MockitoExtension.class)
class OrderEventConsumerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderEventConsumer orderEventConsumer;

    private static final String DUMMY_MESSAGE = "test-message";

    @BeforeEach
    void setUp() {
        // Nada a configurar aqui, pois @InjectMocks e @Mock já fazem o trabalho.
    }

    @Test
    void testHandleOrderCreatedEvent_shouldCallSendOrderCreatedReminder() {
        // Ação: Chamar o método do consumidor
        orderEventConsumer.handleOrderCreatedEvent(DUMMY_MESSAGE);

        // Verificação: Verificar se o método sendOrderCreatedReminder foi chamado no mock
        verify(notificationService).sendOrderCreatedReminder("Your order has been created successfully!");
    }

    @Test
    void testHandleOrderFinalizedEvent_shouldCallSendReminder() {
        // Ação: Chamar o método do consumidor
        orderEventConsumer.handleOrderFinalizedEvent(DUMMY_MESSAGE);

        // Verificação: Verificar se o método sendReminder foi chamado no mock
        verify(notificationService).sendReminder("Your order has been finalized successfully!");
    }
}