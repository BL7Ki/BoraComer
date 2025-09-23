package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pos.java.bora_comer.config.rabbitmq.RabbitMQConfig;
import pos.java.bora_comer.core.port.NotificationService;

@Component
public class OrderEventConsumer {

    private final NotificationService notificationService;

    private static final String ORDER_CREATED_MESSAGE = "Your order has been created successfully!";
    private static final String ORDER_FINALIZED_MESSAGE = "Your order has been finalized successfully!";

    public OrderEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreatedEvent(String message) {
        System.out.println("Event 'Order Created' received: " + message);
        notificationService.sendOrderCreatedReminder(ORDER_CREATED_MESSAGE);
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_FINALIZED_QUEUE)
    public void handleOrderFinalizedEvent(String message) {
        System.out.println("Event 'Order Finalized' received: " + message);
        notificationService.sendReminder(ORDER_FINALIZED_MESSAGE);
    }
}
