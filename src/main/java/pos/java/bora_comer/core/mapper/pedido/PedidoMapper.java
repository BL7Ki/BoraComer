package pos.java.bora_comer.core.mapper.pedido;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoRequestDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;

public interface PedidoMapper {

    Pedido toDomain(PedidoRequestDTO pedidoRequestDTO);
      
    Pedido toDomain(PedidoEntity pedidoEntity);

    PedidoResponseDTO toResponseDTO(Pedido pedido);

    Pedido toDomain(PedidoUpdateRequestDTO pedidoUpdateRequestDTO, Long id, Long restaurantId, Long userId);


    PedidoEntity toEntity(Pedido pedido);
}
