package pos.java.bora_comer.infra.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pos.java.bora_comer.config.rabbitmq.RabbitMQConfig;

@Component
public class OrderEventConsumer {

    private final NotificationServiceImpl notificationServiceImpl;

    private final String REMINDER_MESSAGE = "Lembrete: Seu pedido foi finalizado com sucesso!";
    private final String APPOINTMENT_MESSAGE = "Lembrete: Você tem uma consulta agendada.";

    public OrderEventConsumer(NotificationServiceImpl notificationServiceImpl) {
        this.notificationServiceImpl = notificationServiceImpl;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void receiveOrderCreatedEvent(String message) {
        System.out.println("Evento 'Pedido Criado' recebido: " + message);
        System.out.println("Ação: Enviando lembrete de pedido criado...");
        notificationServiceImpl.enviarLembretePedidoCriado(REMINDER_MESSAGE);
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_FINALIZED_QUEUE)
    public void receiveOrderFinalizedEvent(String message) {
        System.out.println("Evento 'Pedido Finalizado' recebido: " + message);
        System.out.println("Ação: Enviando notificação de pedido finalizado...");

        notificationServiceImpl.enviarLembretePaciente(APPOINTMENT_MESSAGE);
    }
}
