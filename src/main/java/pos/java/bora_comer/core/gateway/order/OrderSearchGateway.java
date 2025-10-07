package pos.java.bora_comer.core.gateway.order;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.order.Order;


public interface OrderSearchGateway {

    Order findById(Long id);

    Page<Order> findAll(int page, int size);
}
