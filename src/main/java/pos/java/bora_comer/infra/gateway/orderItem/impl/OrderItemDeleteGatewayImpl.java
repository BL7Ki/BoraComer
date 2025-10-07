package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;

@Component
public class OrderItemDeleteGatewayImpl implements OrderItemDeleteGateway {

    private final OrderItemRepository orderItemRepository;

    public OrderItemDeleteGatewayImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        var orderItemEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Item de Pedido com ID " + id + " não encontrado."));

        orderItemRepository.delete(orderItemEntity);
    }
}
