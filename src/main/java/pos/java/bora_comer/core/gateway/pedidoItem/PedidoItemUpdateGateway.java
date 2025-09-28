package pos.java.bora_comer.core.gateway.pedidoItem;

import java.util.Optional;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;

public interface PedidoItemUpdateGateway {

    PedidoItem update(PedidoItem pedidoItem) throws PedidoItemDomainException;

    Optional<PedidoItem> findById(Long id);
}
