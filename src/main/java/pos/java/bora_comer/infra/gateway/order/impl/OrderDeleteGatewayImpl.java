package pos.java.bora_comer.infra.gateway.order.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.order.OrderDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;

@Component
public class OrderDeleteGatewayImpl implements OrderDeleteGateway {

    private final OrderRepository orderRepository;

    public OrderDeleteGatewayImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        var orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Pedido com ID " + id + " não encontrado."));

        orderRepository.delete(orderEntity);
    }
}
