package pos.java.bora_comer.core.gateway.order;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.order.Order;

import java.util.List;

public interface OrderSearchGateway {

    Order findById(Long id);

    Page<Order> findAll(int page, int size);

    // Buscar pedidos por ID de usuário.
    List<Order> findAllByUserId(Long userId);
}