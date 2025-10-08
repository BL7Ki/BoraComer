package pos.java.bora_comer.core.gateway.order;

import java.util.Optional;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;

public interface OrderUpdateGateway {

    Order update(Order order) throws OrderDomainException;

    Optional<Order> findById(Long id);
}
