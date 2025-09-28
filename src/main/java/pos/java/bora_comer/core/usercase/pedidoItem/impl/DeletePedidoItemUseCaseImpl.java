package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemDeleteGateway;
import pos.java.bora_comer.core.usercase.pedidoItem.DeletePedidoItemUseCase;

@Service
public class DeletePedidoItemUseCaseImpl implements DeletePedidoItemUseCase {

    private final PedidoItemDeleteGateway pedidoItemDeleteGateway;

    public DeletePedidoItemUseCaseImpl(PedidoItemDeleteGateway pedidoItemDeleteGateway) {
        this.pedidoItemDeleteGateway = pedidoItemDeleteGateway;
    }

    @Override
    public void execute(Long id) {
        pedidoItemDeleteGateway.deleteById(id);
    }
}
