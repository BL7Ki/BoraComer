package pos.java.bora_comer.infra.gateway.order.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.order.OrderSearchGateway;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;


@Component
public class OrderSearchGatewayImpl implements OrderSearchGateway {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderSearchGatewayImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDomain)
                .orElseThrow(() -> new SummerNotFoundException("Pedido com ID " + id + " não encontrado."));
    }

    @Override
    public Page<Order> findAll(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size))
                .map(orderMapper::toDomain);
    }
}
