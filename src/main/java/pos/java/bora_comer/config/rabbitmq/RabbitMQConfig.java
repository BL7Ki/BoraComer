package pos.java.bora_comer.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "bora-comer-exchange";

    // Fila e chave de roteamento para eventos de pedido criado
    public static final String ORDER_CREATED_QUEUE = "order-created-queue";
    public static final String ORDER_CREATED_ROUTING_KEY = "notification.order.created";

    // Fila e chave de roteamento para eventos de pedido finalizado
    public static final String ORDER_FINALIZED_QUEUE = "order-finalized-queue";
    public static final String ORDER_FINALIZED_ROUTING_KEY = "notification.order.finalized";

    // --- Definição das Filas ---
    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true); // Fila durável
    }

    @Bean
    public Queue orderFinalizedQueue() {
        return new Queue(ORDER_FINALIZED_QUEUE, true); // Fila durável
    }

    // --- Definição da Exchange ---
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // --- Vínculos, pra onde vai (Bindings) ---
    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(orderCreatedQueue)
                .to(topicExchange)
                .with(ORDER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding orderFinalizedBinding(Queue orderFinalizedQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(orderFinalizedQueue)
                .to(topicExchange)
                .with(ORDER_FINALIZED_ROUTING_KEY);
    }
}
