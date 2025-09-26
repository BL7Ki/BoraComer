package pos.java.bora_comer.infra.rabbitmq;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationServiceImplTest {

    private final NotificationServiceImpl notificationService = new NotificationServiceImpl();
    private static final String TEST_MESSAGE = "Testing notification";


    @Test
    void testSendReminder_shouldReturnTrueOnSuccess() {
        boolean result = notificationService.sendReminder(TEST_MESSAGE);
        assertTrue(result, "O método sendReminder deve retornar true em caso de sucesso (sem exceção).");
    }

    @Test
    void testSendOrderCreatedReminder_shouldReturnTrueOnSuccess() {
        boolean result = notificationService.sendOrderCreatedReminder(TEST_MESSAGE);
        assertTrue(result, "O método sendOrderCreatedReminder deve retornar true em caso de sucesso.");
    }
}
