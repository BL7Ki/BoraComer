package pos.java.bora_comer.core.gateway.orderItem;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;

public interface OrderItemCreateGateway {
    OrderItem save(OrderItem orderItem);
}
