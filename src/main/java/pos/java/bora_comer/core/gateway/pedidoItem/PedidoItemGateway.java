package pos.java.bora_comer.core.gateway.pedidoItem;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;

import java.util.List;
import java.util.Optional;

public interface PedidoItemGateway {
    PedidoItem save(PedidoItem pedido);
    List<PedidoItem> findAll();
    Optional<PedidoItem> findById(Long id);
    PedidoItem update(PedidoItem pedidoItem);
    void deleteById(Long id);
}
