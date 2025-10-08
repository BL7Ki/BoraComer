package pos.java.bora_comer.core.port;

public interface NotificationService {

    boolean sendReminder(Long userId, String message);

    boolean sendOrderCreatedReminder(Long userId, String reminderMessage);
}