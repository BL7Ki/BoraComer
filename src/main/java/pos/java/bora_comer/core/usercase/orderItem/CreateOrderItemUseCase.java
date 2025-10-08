package pos.java.bora_comer.core.usercase.orderItem;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;

public interface CreateOrderItemUseCase {

    OrderItem execute(OrderItem orderItem) throws OrderItemDomainException;
}
