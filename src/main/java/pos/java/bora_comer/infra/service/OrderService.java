package pos.java.bora_comer.infra.service;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.rabbitmq.OrderEventProducer;

@Service
public class OrderService implements pos.java.bora_comer.core.usercase.order.CreateOrderUseCase {

    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }

    @Override
    public Order execute(Order orderDomain) {

        Order persistedOrder = orderDomain;

        orderEventProducer.sendOrderCreatedEvent(persistedOrder);

        System.out.println("OrderService: Order created successfully and event sent.");

        return persistedOrder;
    }
}