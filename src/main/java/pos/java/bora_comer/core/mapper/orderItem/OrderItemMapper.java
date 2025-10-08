package pos.java.bora_comer.core.mapper.orderItem;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemRequestDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

public interface OrderItemMapper {

    OrderItem toDomain(OrderItemRequestDTO ordertemRequestDTO);
      
    OrderItem toDomain(OrderItemEntity orderEntity);

    OrderItemResponseDTO toResponseDTO(OrderItem order);

    OrderItem toDomain(OrderItemUpdateRequestDTO orderItemUpdateRequestDTO, Long id, Long orderId, Long menuItemId);


    OrderItemEntity toEntity(OrderItem order);
}
