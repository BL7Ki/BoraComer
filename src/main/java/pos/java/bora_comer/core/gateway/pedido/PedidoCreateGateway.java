package pos.java.bora_comer.core.gateway.pedido;

import pos.java.bora_comer.core.domain.pedido.Pedido;

public interface PedidoCreateGateway {
    Pedido save(Pedido pedido);
}
