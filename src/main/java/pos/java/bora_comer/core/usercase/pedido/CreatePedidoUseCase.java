package pos.java.bora_comer.core.usercase.pedido;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.PedidoDomainException;

public interface CreatePedidoUseCase {

    Pedido execute(Pedido pedido) throws PedidoDomainException;
}
