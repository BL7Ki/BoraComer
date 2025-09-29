package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemUpdateGateway;
import pos.java.bora_comer.core.usercase.orderItem.UpdateOrderItemUseCase;

@Service
public class UpdateOrderItemUseCaseImpl implements UpdateOrderItemUseCase {

    private final OrderItemUpdateGateway orderItemUpdateGateway;

    public UpdateOrderItemUseCaseImpl(OrderItemUpdateGateway orderItemUpdateGateway) {
        this.orderItemUpdateGateway = orderItemUpdateGateway;
    }

    @Override
    public OrderItem execute(OrderItem orderItem) {
        return orderItemUpdateGateway.update(orderItem);
    }

     @Override
    public OrderItem findById(Long id) throws OrderItemDomainException {
        return orderItemUpdateGateway.findById(id)
                .orElseThrow(() -> new OrderItemDomainException("Item do Pedido com ID " + id + " não encontrado"));
    }
}
