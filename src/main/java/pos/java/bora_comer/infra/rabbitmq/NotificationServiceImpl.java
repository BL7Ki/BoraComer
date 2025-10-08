package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.port.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public boolean sendReminder(Long userId, String message) {
        try {
            log.info("Reminder sent to User ID {}: {}", userId, message);
            return true;
        } catch (Exception e) {
            log.error("Error sending reminder to User ID {}: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean sendOrderCreatedReminder(Long userId, String reminderMessage) {
        try {
            log.info("Order Created Reminder sent to User ID {}: {}", userId, reminderMessage);
            return true;
        } catch (Exception e) {
            log.error("Error sending order created reminder to User ID {}: {}", userId, e.getMessage(), e);
            return false;
        }
    }
}