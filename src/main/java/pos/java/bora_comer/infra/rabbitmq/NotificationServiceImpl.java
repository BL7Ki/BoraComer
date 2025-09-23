package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.port.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public boolean sendReminder(String message) {
        try {
            System.out.println("Reminder: " + message);
            return true;
        } catch (Exception e) {
            System.out.println("Error sending reminder: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendOrderCreatedReminder(String reminderMessage) {
        try {
            System.out.println("Order Created Reminder: " + reminderMessage);
            return true;
        } catch (Exception e) {
            System.out.println("Error sending order created reminder: " + e.getMessage());
            return false;
        }
    }
}
