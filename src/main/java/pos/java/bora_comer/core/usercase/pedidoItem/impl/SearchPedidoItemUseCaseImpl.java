package pos.java.bora_comer.core.usercase.pedidoItem.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemSearchGateway;
import pos.java.bora_comer.core.usercase.pedidoItem.SearchPedidoItemUseCase;


@Service
public class SearchPedidoItemUseCaseImpl implements SearchPedidoItemUseCase {

    private final PedidoItemSearchGateway pedidoItemSearchGateway;

    public SearchPedidoItemUseCaseImpl(PedidoItemSearchGateway pedidoItemSearchGateway) {
        this.pedidoItemSearchGateway = pedidoItemSearchGateway;
    }

    @Override
    public PedidoItem findById(Long id) {
        return pedidoItemSearchGateway.findById(id);
    }

    @Override
    public Page<PedidoItem> findAll(int page, int size) {
        return pedidoItemSearchGateway.findAll(page, size);
    }
}
