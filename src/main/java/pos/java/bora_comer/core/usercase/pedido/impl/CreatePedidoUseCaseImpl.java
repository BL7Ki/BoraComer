package pos.java.bora_comer.core.usercase.pedido.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.gateway.pedido.PedidoCreateGateway;
import pos.java.bora_comer.core.usercase.pedido.CreatePedidoUseCase;


@Service
public class CreatePedidoUseCaseImpl implements CreatePedidoUseCase {

    private final PedidoCreateGateway pedidoCreateGateway;

    public CreatePedidoUseCaseImpl(PedidoCreateGateway pedidoCreateGateway) {
        this.pedidoCreateGateway = pedidoCreateGateway;
    }

    @Override
    public Pedido execute(Pedido pedido) {
        return pedidoCreateGateway.save(pedido);
    }
}
