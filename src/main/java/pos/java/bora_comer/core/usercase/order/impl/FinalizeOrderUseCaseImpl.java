package pos.java.bora_comer.core.usercase.order.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.gateway.order.OrderUpdateGateway;
import pos.java.bora_comer.core.usercase.order.FinalizeOrderUseCase;

@Service
public class FinalizeOrderUseCaseImpl implements FinalizeOrderUseCase {

    private final OrderUpdateGateway orderUpdateGateway;

    public FinalizeOrderUseCaseImpl(OrderUpdateGateway orderUpdateGateway) {
        this.orderUpdateGateway = orderUpdateGateway;
    }

    @Override
    public Order execute(Long orderId) {
        Order orderToFinalize = orderUpdateGateway.findById(orderId)
                .orElseThrow(() -> new OrderDomainException("Pedido com ID " + orderId + " não encontrado para finalização."));

        Order finalizedOrder = orderToFinalize.updateStatus(OrderStatusEnum.FINALIZED);

        return orderUpdateGateway.update(finalizedOrder);
    }
}