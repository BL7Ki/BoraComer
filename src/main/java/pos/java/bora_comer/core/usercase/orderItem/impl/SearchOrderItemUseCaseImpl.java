package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemSearchGateway;
import pos.java.bora_comer.core.usercase.orderItem.SearchOrderItemUseCase;


@Service
public class SearchOrderItemUseCaseImpl implements SearchOrderItemUseCase {

    private final OrderItemSearchGateway orderItemSearchGateway;

    public SearchOrderItemUseCaseImpl(OrderItemSearchGateway orderItemSearchGateway) {
        this.orderItemSearchGateway = orderItemSearchGateway;
    }

    @Override
    public OrderItem findById(Long id) {
        return orderItemSearchGateway.findById(id);
    }

    @Override
    public Page<OrderItem> findAll(int page, int size) {
        return orderItemSearchGateway.findAll(page, size);
    }
}
