package pos.java.bora_comer.core.usercase.orderItem;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface SearchOrderItemUseCase {

    OrderItem findById(Long id) throws SummerNotFoundException;

    Page<OrderItem> findAll(int page, int size) throws OrderItemDomainException;
}
