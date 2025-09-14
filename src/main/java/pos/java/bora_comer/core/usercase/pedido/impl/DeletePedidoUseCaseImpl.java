package pos.java.bora_comer.core.usercase.pedido.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.gateway.pedido.PedidoDeleteGateway;
import pos.java.bora_comer.core.usercase.pedido.DeletePedidoUseCase;

@Service
public class DeletePedidoUseCaseImpl implements DeletePedidoUseCase {

    private final PedidoDeleteGateway pedidoDeleteGateway;

    public DeletePedidoUseCaseImpl(PedidoDeleteGateway pedidoDeleteGateway) {
        this.pedidoDeleteGateway = pedidoDeleteGateway;
    }

    @Override
    public void execute(Long id) {
        pedidoDeleteGateway.deleteById(id);
    }
}
