package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pos.java.bora_comer.config.rabbitmq.RabbitMQConfig;
import pos.java.bora_comer.core.port.NotificationService;
import pos.java.bora_comer.infra.delivery.order.dto.OrderMessageDTO;

@Component
public class OrderEventConsumer {

    private final NotificationService notificationService;

    private static final String ORDER_CREATED_MESSAGE = "Your order has been created successfully!";
    private static final String ORDER_FINALIZED_MESSAGE = "Your order has been finalized successfully!";

    public OrderEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreatedEvent(OrderMessageDTO orderDTO) {
        System.out.println("Event 'Order Created' received for ID: " + orderDTO.id() + " (User: " + orderDTO.userId() + ")");
        notificationService.sendOrderCreatedReminder(orderDTO.userId(), ORDER_CREATED_MESSAGE);
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_FINALIZED_QUEUE)
    public void handleOrderFinalizedEvent(OrderMessageDTO orderDTO) {
        System.out.println("Event 'Order Finalized' received for ID: " + orderDTO.id() + " (User: " + orderDTO.userId() + ")");

        notificationService.sendReminder(orderDTO.userId(), ORDER_FINALIZED_MESSAGE);
    }
}