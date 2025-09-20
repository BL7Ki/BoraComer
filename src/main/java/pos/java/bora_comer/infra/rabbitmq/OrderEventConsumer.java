package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pos.java.bora_comer.config.rabbitmq.RabbitMQConfig;

@Component
public class OrderEventConsumer {

    //Ouve a fila de eventos de pedido criado e processa a mensagem.
    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void receiveOrderCreatedEvent(String message) {
        System.out.println("Evento 'Pedido Criado' recebido: " + message);
        System.out.println("Ação: Enviando lembrete de pedido criado...");
    }

    //Ouve a fila de eventos de pedido finalizado e processa a mensagem.
    @RabbitListener(queues = RabbitMQConfig.ORDER_FINALIZED_QUEUE)
    public void receiveOrderFinalizedEvent(String message) {
        System.out.println("Evento 'Pedido Finalizado' recebido: " + message);
        System.out.println("Ação: Enviando notificação de pedido finalizado...");
    }
}
