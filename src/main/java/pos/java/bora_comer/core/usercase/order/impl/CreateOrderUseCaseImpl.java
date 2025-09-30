package pos.java.bora_comer.core.usercase.order.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.gateway.order.OrderCreateGateway;
import pos.java.bora_comer.core.usercase.order.CreateOrderUseCase;


@Service
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderCreateGateway orderCreateGateway;

    public CreateOrderUseCaseImpl(OrderCreateGateway orderCreateGateway) {
        this.orderCreateGateway = orderCreateGateway;
    }

    @Override
    public Order execute(Order order) {
        return orderCreateGateway.save(order);
    }
}
