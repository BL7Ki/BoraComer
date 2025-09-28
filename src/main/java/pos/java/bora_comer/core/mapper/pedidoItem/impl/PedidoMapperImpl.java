package pos.java.bora_comer.core.mapper.pedidoItem.impl;

import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;
import pos.java.bora_comer.core.mapper.pedidoItem.PedidoItemMapper;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemRequestDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;

@Component
public class PedidoMapperImpl implements PedidoItemMapper {

    @Override
    public PedidoItem toDomain(PedidoItemRequestDTO pedidoItemRequestDTO) {
        if (pedidoItemRequestDTO == null) {
            throw new PedidoItemDomainException("PedidoItemRequestDTO não pode ser nulo");
        }

        return PedidoItem.create(
                null, // ID será gerado pelo banco
                pedidoItemRequestDTO.pedidoId(),
                pedidoItemRequestDTO.menuItemId(),
                pedidoItemRequestDTO.quantity(),
                null // lastModifiedDate será gerado na criação
        );
    }

    @Override
    public PedidoItemEntity toEntity(PedidoItem pedidoItem) {
        if (pedidoItem == null) {
            throw new PedidoItemDomainException("Item do Pedido não pode ser nulo");
        }

        return PedidoItemEntity.create(
                pedidoItem.getPedidoId(),
                pedidoItem.getMenuItemId(),
                pedidoItem.getQuantity()
        );
    }

    @Override
    public PedidoItem toDomain(PedidoItemEntity pedidoEntity) {
        if (pedidoEntity == null) {
            throw new PedidoItemDomainException("PedidoItemEntity não pode ser nulo");
        }

        return PedidoItem.create(
                pedidoEntity.getId(),
                pedidoEntity.getPedidoId(),
                pedidoEntity.getMenuItemId(),
                pedidoEntity.getQuantity(),
                pedidoEntity.getLastModifiedDate()
        );
    }

    @Override
    public PedidoItemResponseDTO toResponseDTO(PedidoItem pedidoItem) {
        if (pedidoItem == null) {
            throw new PedidoItemDomainException("Item do Pedido não pode ser nulo");
        }

        return new PedidoItemResponseDTO(
                pedidoItem.getId(),
                pedidoItem.getPedidoId(),
                pedidoItem.getMenuItemId(),
                pedidoItem.getQuantity(),
                pedidoItem.getLastModifiedDate()
        );
    }

    @Override
    public PedidoItem toDomain(PedidoItemUpdateRequestDTO pedidoItemUpdateRequestDTO, Long id, Long pedidoId, Long menuItemId) {
        if (pedidoItemUpdateRequestDTO == null) {
            throw new PedidoItemDomainException("PedidoItemUpdateRequestDTO não pode ser nulo");
        }

        return PedidoItem.create(
                id,
                pedidoId,
                menuItemId,
                pedidoItemUpdateRequestDTO.quantity(),
                null // lastModifiedDate será atualizado na atualização
        );
    }
}
