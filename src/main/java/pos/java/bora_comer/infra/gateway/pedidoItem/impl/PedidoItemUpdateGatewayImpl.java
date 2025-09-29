package pos.java.bora_comer.infra.gateway.pedidoItem.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;
import pos.java.bora_comer.core.gateway.pedidoItem.PedidoItemUpdateGateway;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.PedidoItemRepository;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;

import java.util.Optional;

@Component
public class PedidoItemUpdateGatewayImpl implements PedidoItemUpdateGateway {

    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoItemMapper pedidoItemMapper;
    private final PedidoRepository pedidoRepository;
    private final MenuItemRepository menuItemRepository;

    public PedidoItemUpdateGatewayImpl(PedidoItemRepository pedidoItemRepository,
                                       PedidoItemMapper pedidoItemMapper,
                                       PedidoRepository pedidoRepository,
                                       MenuItemRepository menuItemRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoItemMapper = pedidoItemMapper;
        this.pedidoRepository = pedidoRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    @Override
    public PedidoItem update(PedidoItem pedidoItem) throws PedidoItemDomainException {
        PedidoItemEntity entity = pedidoItemRepository.findById(pedidoItem.getId())
                .orElseThrow(() -> new PedidoItemDomainException("Item de Pedido com ID " + pedidoItem.getId() + " não encontrado."));

        if (!pedidoItem.getPedidoId().equals(entity.getPedidoId())) {
            pedidoRepository.findById(pedidoItem.getPedidoId())
                    .orElseThrow(() -> new PedidoItemDomainException("Pedido com ID " + pedidoItem.getPedidoId() + " não encontrado."));
            entity.updatePedidoId(pedidoItem.getPedidoId());
        }

        if (!pedidoItem.getMenuItemId().equals(entity.getMenuItemId())) {
            menuItemRepository.findById(pedidoItem.getMenuItemId())
                    .orElseThrow(() -> new PedidoItemDomainException("Item de Menu com ID " + pedidoItem.getMenuItemId() + " não encontrado."));
            entity.updateMenuItemId(pedidoItem.getMenuItemId());
        }

        entity.updateQuantity(pedidoItem.getQuantity());
        entity.updateLastModifiedDate();

       PedidoItemEntity updatedEntity = pedidoItemRepository.save(entity);
        return pedidoItemMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<PedidoItem> findById(Long id) {
        return pedidoItemRepository.findById(id)
                .map(pedidoItemMapper::toDomain);
    }
}
