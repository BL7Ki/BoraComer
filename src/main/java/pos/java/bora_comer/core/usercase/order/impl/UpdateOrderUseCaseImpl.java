package pos.java.bora_comer.core.usercase.order.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.gateway.order.OrderUpdateGateway;
import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;

@Service
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {

    private final OrderUpdateGateway orderUpdateGateway;

    public UpdateOrderUseCaseImpl(OrderUpdateGateway orderUpdateGateway) {
        this.orderUpdateGateway = orderUpdateGateway;
    }

    @Override
    public Order execute(Order order) {
        return orderUpdateGateway.update(order);
    }

     @Override
    public Order findById(Long id) throws OrderDomainException {
        return orderUpdateGateway.findById(id)
                .orElseThrow(() -> new OrderDomainException("Pedido com ID " + id + " não encontrado"));
    }
}
