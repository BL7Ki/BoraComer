package pos.java.bora_comer.core.gateway.pedido;

import java.util.Optional;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.PedidoDomainException;

public interface PedidoUpdateGateway {

    Pedido update(Pedido pedido) throws PedidoDomainException;

    Optional<Pedido> findById(Long id);
}
