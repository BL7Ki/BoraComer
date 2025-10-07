package pos.java.bora_comer.core.usercase.order;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface SearchOrderUseCase {

    Order findById(Long id) throws SummerNotFoundException;

    Page<Order> findAll(int page, int size) throws OrderDomainException;
}
