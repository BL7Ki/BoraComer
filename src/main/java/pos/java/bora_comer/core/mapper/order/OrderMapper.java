package pos.java.bora_comer.core.mapper.order;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
// O DTO de update pode ser removido se nenhum outro Mapper o usar
// import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

public interface OrderMapper {

    Order toDomain(OrderRequestDTO orderRequestDTO);

    Order toDomain(OrderEntity orderEntity);

    OrderResponseDTO toResponseDTO(Order order);

    OrderEntity toEntity(Order order);
}