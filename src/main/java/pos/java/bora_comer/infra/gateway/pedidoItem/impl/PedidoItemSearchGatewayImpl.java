package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemSearchGateway;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;


@Component
public class PedidoItemSearchGatewayImpl implements PedidoItemSearchGateway {

    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoItemMapper pedidoItemMapper;

    public PedidoItemSearchGatewayImpl(PedidoItemRepository pedidoItemRepository, PedidoItemMapper pedidoItemMapper) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoItemMapper = pedidoItemMapper;
    }

    @Override
    public PedidoItem findById(Long id) {
        return pedidoItemRepository.findById(id)
                .map(pedidoItemMapper::toDomain)
                .orElseThrow(() -> new SummerNotFoundException("Item de Pedido com ID " + id + " não encontrado."));
    }

    @Override
    public Page<PedidoItem> findAll(int page, int size) {
        return pedidoItemRepository.findAll(PageRequest.of(page, size))
                .map(pedidoItemMapper::toDomain);
    }
}
