package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemUpdateGateway;
import pos.java.bora_comer.core.usercase.pedidoItem.UpdatePedidoItemUseCase;

@Service
public class UpdatePedidoItemUseCaseImpl implements UpdatePedidoItemUseCase {

    private final PedidoItemUpdateGateway pedidoItemUpdateGateway;

    public UpdatePedidoItemUseCaseImpl(PedidoItemUpdateGateway pedidoItemUpdateGateway) {
        this.pedidoItemUpdateGateway = pedidoItemUpdateGateway;
    }

    @Override
    public PedidoItem execute(PedidoItem pedidoItem) {
        return pedidoItemUpdateGateway.update(pedidoItem);
    }

     @Override
    public PedidoItem findById(Long id) throws PedidoItemDomainException {
        return pedidoItemUpdateGateway.findById(id)
                .orElseThrow(() -> new PedidoItemDomainException("Item do Pedido com ID " + id + " não encontrado"));
    }
}
