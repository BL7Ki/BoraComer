package pos.java.bora_comer.core.gateway.pedido;

import pos.java.bora_comer.core.domain.pedido.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoGateway {
    Pedido save(Pedido pedido);
    List<Pedido> findAll();
    Optional<Pedido> findById(Long id);
    Pedido update(Pedido item);
    void deleteById(Long id);
}
