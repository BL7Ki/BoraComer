package pos.java.bora_comer.core.usercase.order;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO; // 👈 Import required

public interface UpdateOrderUseCase {
    Order execute(Order order) throws OrderDomainException;
    Order execute(Long id, OrderUpdateRequestDTO updateDTO) throws OrderDomainException;
    Order findById(Long id) throws OrderDomainException;
}