package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemCreateGateway;
import pos.java.bora_comer.core.usercase.orderItem.CreateOrderItemUseCase;


@Service
public class CreateOrderItemUseCaseImpl implements CreateOrderItemUseCase {

    private final OrderItemCreateGateway orderItemCreateGateway;

    public CreateOrderItemUseCaseImpl(OrderItemCreateGateway orderItemCreateGateway) {
        this.orderItemCreateGateway = orderItemCreateGateway;
    }

    @Override
    public OrderItem execute(OrderItem orderItem) {
        return orderItemCreateGateway.save(orderItem);
    }
}
