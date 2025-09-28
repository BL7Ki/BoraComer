package pos.java.bora_comer.core.gateway.pedidoItem;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;

public interface PedidoItemCreateGateway {
    PedidoItem save(PedidoItem pedidoItem);
}
