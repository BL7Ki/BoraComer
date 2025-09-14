package pos.java.bora_comer.core.usercase.pedido.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.gateway.pedido.PedidoSearchGateway;
import pos.java.bora_comer.core.usercase.pedido.SearchPedidoUseCase;


@Service
public class SearchPedidoUseCaseImpl implements SearchPedidoUseCase {

    private final PedidoSearchGateway pedidoSearchGateway;

    public SearchPedidoUseCaseImpl(PedidoSearchGateway pedidoSearchGateway) {
        this.pedidoSearchGateway = pedidoSearchGateway;
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoSearchGateway.findById(id);
    }

    @Override
    public Page<Pedido> findAll(int page, int size) {
        return pedidoSearchGateway.findAll(page, size);
    }
}
