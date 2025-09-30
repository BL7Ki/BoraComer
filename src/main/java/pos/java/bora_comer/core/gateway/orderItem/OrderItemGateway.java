package pos.java.bora_comer.core.gateway.orderItem;

import java.util.List;
import java.util.Optional;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;

public interface OrderItemGateway {
    OrderItem save(OrderItem order);
    List<OrderItem> findAll();
    Optional<OrderItem> findById(Long id);
    OrderItem update(OrderItem orderItem);
    void deleteById(Long id);
}
