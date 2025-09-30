package pos.java.bora_comer.core.gateway.orderItem;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;


public interface OrderItemSearchGateway {

    OrderItem findById(Long id);
    
    Page<OrderItem> findAll(int page, int size);
}
