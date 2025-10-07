package pos.java.bora_comer.core.usercase.order.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.gateway.order.OrderDeleteGateway;
import pos.java.bora_comer.core.usercase.order.DeleteOrderUseCase;

@Service
public class DeleteOrderUseCaseImpl implements DeleteOrderUseCase {

    private final OrderDeleteGateway orderDeleteGateway;

    public DeleteOrderUseCaseImpl(OrderDeleteGateway orderDeleteGateway) {
        this.orderDeleteGateway = orderDeleteGateway;
    }

    @Override
    public void execute(Long id) {
        orderDeleteGateway.deleteById(id);
    }
}
