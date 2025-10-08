package pos.java.bora_comer.core.usercase.order;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

import java.util.List;

public interface SearchOrderUseCase {

    Order findById(Long id) throws SummerNotFoundException;

    Page<Order> findAll(int page, int size) throws OrderDomainException;

    // Para o Field Resolver buscar todos os pedidos de um usuário.
    List<Order> findAllByUserId(Long userId);
}