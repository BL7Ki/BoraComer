package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemSearchGateway;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;


@Component
public class OrderItemSearchGatewayImpl implements OrderItemSearchGateway {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemSearchGatewayImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::toDomain)
                .orElseThrow(() -> new SummerNotFoundException("Item de Pedido com ID " + id + " não encontrado."));
    }

    @Override
    public Page<OrderItem> findAll(int page, int size) {
        return orderItemRepository.findAll(PageRequest.of(page, size))
                .map(orderItemMapper::toDomain);
    }
}
