package pos.java.bora_comer.infra.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.config.rabbitmq.RabbitMQConfig;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.delivery.order.dto.OrderMessageDTO;

@Service
public class OrderEventProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrderCreatedEvent(Order order) {
        sendEvent(RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, order, "Order Created event sent");
    }

    public void sendOrderFinalizedEvent(Order order) {
        sendEvent(RabbitMQConfig.ORDER_FINALIZED_ROUTING_KEY, order, "Order Finalized event sent");
    }

    private void sendEvent(String routingKey, Order order, String logMessage) {
        // Converte o objeto de Domínio (Order) para o DTO de Mensagem
        OrderMessageDTO messageDTO = convertToMessageDTO(order);

        try {
            String message = objectMapper.writeValueAsString(messageDTO);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, message);
            System.out.println(logMessage + ": " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing order event: " + e.getMessage());
        }
    }

    private OrderMessageDTO convertToMessageDTO(Order order) {
        return new OrderMessageDTO(
                order.getId(),
                order.getDateTimeOrder(),
                order.isDelivery(),
                order.getRestaurantId(),
                order.getUserId()
        );
    }
}