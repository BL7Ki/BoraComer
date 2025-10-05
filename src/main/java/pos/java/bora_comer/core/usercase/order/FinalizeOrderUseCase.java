package pos.java.bora_comer.core.usercase.order;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;

public interface FinalizeOrderUseCase {
    Order execute(Long orderId) throws OrderDomainException;
}