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

    // Creates a new order from DTO and sends a notification to the message queue.
    public Order create(CreateOrderDTO createOrderDTO) {

        Order newOrder = Order.create(
                createOrderDTO.clienteId(),
                createOrderDTO.valorTotal()
        );

        // Sends "order created" event to RabbitMQ with the complete Order entity.
        orderEventProducer.sendOrderCreatedEvent(newOrder);

        System.out.println("OrderService: Order " + newOrder.getId() + " successfully created and event sent.");

        return newOrder;
    }
}
