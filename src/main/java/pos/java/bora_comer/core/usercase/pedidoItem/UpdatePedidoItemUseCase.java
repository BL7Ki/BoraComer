package pos.java.bora_comer.core.usercase.pedidoItem;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;

public interface UpdatePedidoItemUseCase {
    PedidoItem execute(PedidoItem pedidoItem) throws PedidoItemDomainException;

    PedidoItem findById(Long id) throws PedidoItemDomainException;
}
