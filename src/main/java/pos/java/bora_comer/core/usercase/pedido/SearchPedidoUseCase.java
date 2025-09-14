package pos.java.bora_comer.core.usercase.pedido;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.PedidoDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface SearchPedidoUseCase {

    Pedido findById(Long id) throws SummerNotFoundException;

    Page<Pedido> findAll(int page, int size) throws PedidoDomainException;
}
