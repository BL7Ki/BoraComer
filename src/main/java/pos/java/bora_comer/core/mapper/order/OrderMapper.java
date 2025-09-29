package pos.java.bora_comer.core.mapper.order;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

public interface OrderMapper {

    Order toDomain(OrderRequestDTO orderRequestDTO);
      
    Order toDomain(OrderEntity orderEntity);

    OrderResponseDTO toResponseDTO(Order order);

    Order toDomain(OrderUpdateRequestDTO orderUpdateRequestDTO, Long id, Long restaurantId, Long userId);


    OrderEntity toEntity(Order order);
}
