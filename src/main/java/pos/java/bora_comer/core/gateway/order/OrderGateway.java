package pos.java.bora_comer.core.gateway.order;

import java.util.List;
import java.util.Optional;

import pos.java.bora_comer.core.domain.order.Order;

public interface OrderGateway {
    Order save(Order order);
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order update(Order order);
    void deleteById(Long id);
}
