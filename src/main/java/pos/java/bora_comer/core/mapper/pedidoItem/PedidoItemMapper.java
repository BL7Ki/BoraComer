package pos.java.bora_comer.core.mapper.pedidoItem;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemRequestDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;

public interface PedidoItemMapper {

    PedidoItem toDomain(PedidoItemRequestDTO pedidoItemRequestDTO);
      
    PedidoItem toDomain(PedidoItemEntity pedidoEntity);

    PedidoItemResponseDTO toResponseDTO(PedidoItem pedido);

    PedidoItem toDomain(PedidoItemUpdateRequestDTO pedidoItemUpdateRequestDTO, Long id, Long pedidoId, Long menuItemId);


    PedidoItemEntity toEntity(PedidoItem pedido);
}
