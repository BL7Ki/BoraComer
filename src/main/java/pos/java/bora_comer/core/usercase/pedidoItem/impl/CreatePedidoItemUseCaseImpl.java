package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemCreateGateway;
import pos.java.bora_comer.core.usercase.pedidoItem.CreatePedidoItemUseCase;


@Service
public class CreatePedidoItemUseCaseImpl implements CreatePedidoItemUseCase {

    private final PedidoItemCreateGateway pedidoItemCreateGateway;

    public CreatePedidoItemUseCaseImpl(PedidoItemCreateGateway pedidoItemCreateGateway) {
        this.pedidoItemCreateGateway = pedidoItemCreateGateway;
    }

    @Override
    public PedidoItem execute(PedidoItem pedidoItem) {
        return pedidoItemCreateGateway.save(pedidoItem);
    }
}
