package pos.java.bora_comer.core.usercase.order.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.gateway.order.OrderSearchGateway;
import pos.java.bora_comer.core.usercase.order.SearchOrderUseCase;

import java.util.List;

@Service
public class SearchOrderUseCaseImpl implements SearchOrderUseCase {

    private final OrderSearchGateway orderSearchGateway;

    public SearchOrderUseCaseImpl(OrderSearchGateway orderSearchGateway) {
        this.orderSearchGateway = orderSearchGateway;
    }

    @Override
    public Order findById(Long id) {
        return orderSearchGateway.findById(id);
    }

    @Override
    public Page<Order> findAll(int page, int size) {
        return orderSearchGateway.findAll(page, size);
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return orderSearchGateway.findAllByUserId(userId);
    }
}