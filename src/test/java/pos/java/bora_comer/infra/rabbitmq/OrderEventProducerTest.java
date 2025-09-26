package pos.java.bora_comer.infra.rabbitmq;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.anyString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import pos.java.bora_comer.config.rabbitmq.RabbitMQConfig;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.util.factory.OrderTestFactory;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;

@ExtendWith(MockitoExtension.class)
class OrderEventProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderEventProducer orderEventProducer;

    private Order createdOrder;
    private String jsonCreatedOrder;

    @BeforeEach
    void setUp() {
        // 1. Usa a Factory para criar o objeto de teste com status CREATED (padrão)
        createdOrder = OrderTestFactory.createDefaultOrder();

        // 2. Define a string JSON esperada para a ordem padrão.
        // O JSON deve ser consistente com os valores DEFAULTS da OrderTestFactory
        jsonCreatedOrder = "{\"id\":999,\"clienteId\":\"client-xyz-123\",\"status\":\"CREATED\",\"dataCriacao\":\"2025-09-25T10:00:00\",\"valorTotal\":150.75}";

        // O STUBBING FOI REMOVIDO DAQUI para o método @Test onde é usado.
    }

    @Test
    void testSendOrderCreatedEvent_shouldSerializeAndSendToOrderCreatedQueue() throws JsonProcessingException {
        // Mocking: Define o comportamento necessário apenas para este teste
        when(objectMapper.writeValueAsString(createdOrder)).thenReturn(jsonCreatedOrder);

        // Ação
        orderEventProducer.sendOrderCreatedEvent(createdOrder);

        // Verificação:
        verify(objectMapper).writeValueAsString(createdOrder);
        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ORDER_CREATED_ROUTING_KEY,
                jsonCreatedOrder
        );
    }

    @Test
    void testSendOrderFinalizedEvent_shouldSerializeAndSendToOrderFinalizedQueue() throws JsonProcessingException {
        // Prepara uma ordem com status FINALIZED
        Order finalizedOrder = OrderTestFactory.createOrderWithStatus(OrderStatusEnum.FINALIZED);
        String jsonFinalizedOrder = jsonCreatedOrder.replace("CREATED", "FINALIZED");

        // Mocking: Define o comportamento necessário apenas para este teste
        when(objectMapper.writeValueAsString(finalizedOrder)).thenReturn(jsonFinalizedOrder);

        // Ação
        orderEventProducer.sendOrderFinalizedEvent(finalizedOrder);

        // Verificação:
        verify(objectMapper).writeValueAsString(finalizedOrder);
        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ORDER_FINALIZED_ROUTING_KEY,
                jsonFinalizedOrder
        );
    }

    @Test
    void testSendOrderCreatedEvent_whenSerializationFails_shouldCatchExceptionAndLogError() throws JsonProcessingException {
        // Mocking: Configura o mock para lançar uma exceção de serialização
        when(objectMapper.writeValueAsString(createdOrder)).thenThrow(JsonProcessingException.class);

        // Ação (a exceção deve ser capturada internamente)
        orderEventProducer.sendOrderCreatedEvent(createdOrder);

        // Verificação: Garante que NENHUMA mensagem foi enviada para o RabbitMQ (após a falha)
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), anyString());
    }
}