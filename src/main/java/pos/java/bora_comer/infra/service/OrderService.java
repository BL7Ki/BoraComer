package pos.java.bora_comer.infra.service;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.delivery.order.dto.CreateOrderDTO;
import pos.java.bora_comer.infra.rabbitmq.OrderEventProducer;

@Service
public class OrderService {

    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }

    //Cria um novo pedido a partir de um DTO e envia uma notificação para a fila de mensagens.
    public Order create(CreateOrderDTO createOrderDTO) {

        Order newOrder = Order.create(
                createOrderDTO.clienteId(),
                createOrderDTO.valorTotal()
        );

        // Envia o evento de "pedido criado" para o RabbitMQ, passando a entidade Order completa.
        orderEventProducer.sendOrderCreatedEvent(newOrder);

        System.out.println("Serviço de Pedido: Pedido " + newOrder.getId() + " criado com sucesso e evento enviado.");

        return newOrder;
    }
}
