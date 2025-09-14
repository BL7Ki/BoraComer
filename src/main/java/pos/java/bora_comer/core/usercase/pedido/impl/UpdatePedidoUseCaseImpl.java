package pos.java.bora_comer.core.usercase.pedido.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.errors.PedidoDomainException;
import pos.java.bora_comer.core.gateway.pedido.PedidoUpdateGateway;
import pos.java.bora_comer.core.usercase.pedido.UpdatePedidoUseCase;

@Service
public class UpdatePedidoUseCaseImpl implements UpdatePedidoUseCase {

    private final PedidoUpdateGateway pedidoUpdateGateway;

    public UpdatePedidoUseCaseImpl(PedidoUpdateGateway pedidoUpdateGateway) {
        this.pedidoUpdateGateway = pedidoUpdateGateway;
    }

    @Override
    public Pedido execute(Pedido pedido) {
        return pedidoUpdateGateway.update(pedido);
    }

     @Override
    public Pedido findById(Long id) throws PedidoDomainException {
        return pedidoUpdateGateway.findById(id)
                .orElseThrow(() -> new MenuItemDomainException("Pedido com ID " + id + " não encontrado"));
    }
}
