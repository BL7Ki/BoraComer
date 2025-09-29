package pos.java.bora_comer.core.gateway.orderItem;

import java.util.Optional;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;

public interface OrderItemUpdateGateway {

    OrderItem update(OrderItem orderItem) throws OrderItemDomainException;

    Optional<OrderItem> findById(Long id);
}
