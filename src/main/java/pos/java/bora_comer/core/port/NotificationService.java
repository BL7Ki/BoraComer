package pos.java.bora_comer.core.port;

public interface NotificationService {

    boolean sendReminder(String message);

    boolean sendOrderCreatedReminder(String reminderMessage);
}
