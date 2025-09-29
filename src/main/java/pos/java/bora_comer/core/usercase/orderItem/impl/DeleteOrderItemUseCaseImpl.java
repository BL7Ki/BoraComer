package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.gateway.orderItem.OrderItemDeleteGateway;
import pos.java.bora_comer.core.usercase.orderItem.DeleteOrderItemUseCase;

@Service
public class DeleteOrderItemUseCaseImpl implements DeleteOrderItemUseCase {

    private final OrderItemDeleteGateway orderItemDeleteGateway;

    public DeleteOrderItemUseCaseImpl(OrderItemDeleteGateway orderItemDeleteGateway) {
        this.orderItemDeleteGateway = orderItemDeleteGateway;
    }

    @Override
    public void execute(Long id) {
        orderItemDeleteGateway.deleteById(id);
    }
}
