package pos.java.bora_comer.core.gateway.order;

import pos.java.bora_comer.core.domain.order.Order;

public interface OrderCreateGateway {
    Order save(Order order);
}
