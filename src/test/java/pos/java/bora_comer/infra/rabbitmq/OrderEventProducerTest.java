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
        createdOrder = OrderTestFactory.createDefault();

        // id, dateTimeOrder, delivery, restaurantId, userId, lastModifiedDate
        // Usamos valores da OrderTestFactory: id: null, dateTimeOrder: "2024-10-10T12:00:00", delivery: true, restaurantId: 1L, userId: 1L, lastModifiedDate: "2024-10-10T12:00:00"
        jsonCreatedOrder = "{\"id\":null,\"dateTimeOrder\":\"2024-10-10T12:00:00\",\"delivery\":true,\"restaurantId\":1,\"userId\":1,\"lastModifiedDate\":\"2024-10-10T12:00:00\"}";
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
    void testSendOrderCreatedEvent_whenSerializationFails_shouldCatchExceptionAndLogError() throws JsonProcessingException {
        // Mocking: Configura o mock para lançar uma exceção de serialização
        when(objectMapper.writeValueAsString(createdOrder)).thenThrow(JsonProcessingException.class);

        // Ação (a exceção deve ser capturada internamente)
        orderEventProducer.sendOrderCreatedEvent(createdOrder);

        // Verificação: Garante que NENHUMA mensagem foi enviada para o RabbitMQ (após a falha)
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), anyString());
    }
}