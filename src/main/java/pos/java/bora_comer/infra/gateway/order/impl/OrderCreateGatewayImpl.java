package pos.java.bora_comer.infra.gateway.order.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.gateway.order.OrderCreateGateway;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;

@Component
public class OrderCreateGatewayImpl implements OrderCreateGateway {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderCreateGatewayImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    
    @Transactional
    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new OrderDomainException("Pedido não pode ser nulo");
        }
        var orderEntity = orderMapper.toEntity(order);
        var savedEntity = orderRepository.save(orderEntity);

        return orderMapper.toDomain(savedEntity);
    }
}
