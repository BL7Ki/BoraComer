package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemCreateGateway;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;

@Component
public class PedidoItemCreateGatewayImpl implements PedidoItemCreateGateway {

    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoItemMapper pedidoItemMapper;

    public PedidoItemCreateGatewayImpl(PedidoItemRepository pedidoItemRepository, PedidoItemMapper pedidoItemMapper) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoItemMapper = pedidoItemMapper;
    }

    
    @Transactional
    @Override
    public PedidoItem save(PedidoItem pedidoItem) {
        if (pedidoItem == null) {
            throw new PedidoItemDomainException("Item do Pedido não pode ser nulo");
        }
        var pedidoItemEntity = pedidoItemMapper.toEntity(pedidoItem);
        var savedEntity = pedidoItemRepository.save(pedidoItemEntity);

        return pedidoItemMapper.toDomain(savedEntity);
    }
}
