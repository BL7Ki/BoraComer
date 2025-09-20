package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.config.rabbitmq.RabbitMQConfig;
import pos.java.bora_comer.core.domain.order.Order;

@Service
public class OrderEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envia uma mensagem para a fila de eventos de pedido criado.
     * @param order o objeto do pedido criado.
     */
    public void sendOrderCreatedEvent(Order order) {
        // Você pode serializar o objeto Order para JSON ou enviar apenas o ID.
        // Neste exemplo, vamos enviar o ID do pedido.
        String message = "Pedido criado. ID: " + order.getId() + " - Cliente: " + order.getClienteId();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, message);
        System.out.println("Evento 'Pedido Criado' enviado: " + message);
    }

    /**
     * Envia uma mensagem para a fila de eventos de pedido finalizado.
     * @param order o objeto do pedido finalizado.
     */
    public void sendOrderFinalizedEvent(Order order) {
        // Envia o ID do pedido finalizado.
        String message = "Pedido finalizado. ID: " + order.getId();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ORDER_FINALIZED_ROUTING_KEY, message);
        System.out.println("Evento 'Pedido Finalizado' enviado: " + message);
    }
}
