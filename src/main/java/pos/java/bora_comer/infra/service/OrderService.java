package pos.java.bora_comer.infra.service;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.delivery.order.dto.CreateOrderDTO;
import pos.java.bora_comer.infra.rabbitmq.OrderEventProducer;

import java.time.LocalDateTime; // Importação necessária

@Service
public class OrderService {

    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }

    // Creates a new order from DTO and sends a notification to the message queue.
    public Order create(CreateOrderDTO createOrderDTO) {

        LocalDateTime now = LocalDateTime.now();

        Order newOrder = Order.create(
                now, // dateTimeOrder: Momento da criação
                createOrderDTO.delivery(),
                createOrderDTO.restaurantId(),
                createOrderDTO.userId()
                // lastModifiedDate é preenchido implicitamente no factory
        );

        orderEventProducer.sendOrderCreatedEvent(newOrder);

        System.out.println("OrderService: Order created successfully and event sent.");

        return newOrder;
    }
}