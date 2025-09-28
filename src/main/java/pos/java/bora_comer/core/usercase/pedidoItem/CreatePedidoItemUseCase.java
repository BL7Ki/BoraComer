package pos.java.bora_comer.core.usercase.pedidoItem;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;

public interface CreatePedidoItemUseCase {

    PedidoItem execute(PedidoItem pedidoItem) throws PedidoItemDomainException;
}
