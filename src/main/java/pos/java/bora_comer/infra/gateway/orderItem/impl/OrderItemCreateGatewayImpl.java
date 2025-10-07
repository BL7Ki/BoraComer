package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemCreateGateway;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;

@Component
public class OrderItemCreateGatewayImpl implements OrderItemCreateGateway {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemCreateGatewayImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    
    @Transactional
    @Override
    public OrderItem save(OrderItem orderItem) {
        if (orderItem == null) {
            throw new OrderItemDomainException("Item do Pedido não pode ser nulo");
        }
        var orderItemEntity = orderItemMapper.toEntity(orderItem);
        var savedEntity = orderItemRepository.save(orderItemEntity);

        return orderItemMapper.toDomain(savedEntity);
    }
}
