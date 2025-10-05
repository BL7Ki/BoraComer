package pos.java.bora_comer.infra.gateway.order.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.gateway.order.OrderUpdateGateway;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

import java.util.Optional;

@Component
public class OrderUpdateGatewayImpl implements OrderUpdateGateway {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderUpdateGatewayImpl(OrderRepository orderRepository,
                                  OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public Order update(Order order) throws OrderDomainException {
        if (order.getId() == null) {
            throw new OrderDomainException("O pedido deve ter um ID para ser atualizado.");
        }

        OrderEntity entityToSave = orderMapper.toEntity(order);

        OrderEntity updatedEntity = orderRepository.save(entityToSave);

        return orderMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDomain);
    }
}