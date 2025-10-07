package pos.java.bora_comer.core.mapper.order.impl;

import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toDomain(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO == null) {
            throw new OrderDomainException("OrderRequestDTO não pode ser nulo");
        }

        // Order.create(5 args) que internamente define o status como PENDING.
        return Order.create(
                orderRequestDTO.dateTimeOrder(),
                orderRequestDTO.delivery(),
                orderRequestDTO.restaurantId(),
                orderRequestDTO.userId(),
                orderRequestDTO.lastModifiedDate()
        );
    }

    @Override
    public OrderEntity toEntity(Order order) {
        if (order == null) {
            throw new OrderDomainException("Pedido não pode ser nulo");
        }

        // OrderEntity.create agora exige 5 argumentos, incluindo o status.
        return OrderEntity.create(
                order.getDateTimeOrder(),
                order.isDelivery(),
                order.getRestaurantId(),
                order.getUserId(),
                order.getStatus()
        );
    }

    @Override
    public Order toDomain(OrderEntity orderEntity) {
        if (orderEntity == null) {
            throw new OrderDomainException("OrderEntity não pode ser nulo");
        }

        // Order.create para reconstrução (com ID) exige 7 argumentos, incluindo o status.
        return Order.create(
                orderEntity.getId(),
                orderEntity.getDateTimeOrder(),
                orderEntity.isDelivery(),
                orderEntity.getRestaurantId(),
                orderEntity.getUserId(),
                orderEntity.getLastModifiedDate(),
                orderEntity.getStatus()
        );
    }

    @Override
    public OrderResponseDTO toResponseDTO(Order order) {
        if (order == null) {
            throw new OrderDomainException("Pedido não pode ser nulo");
        }

        // Assumindo que OrderResponseDTO agora tem 7 campos (incluindo o status).
        return new OrderResponseDTO(
                order.getId(),
                order.getDateTimeOrder(),
                order.isDelivery(),
                order.getRestaurantId(),
                order.getUserId(),
                order.getLastModifiedDate(),
                order.getStatus()
        );
    }

    @Override
    public Order toDomain(OrderUpdateRequestDTO orderUpdateRequestDTO, Long id, Long restaurantId, Long userId) {
        if (orderUpdateRequestDTO == null) {
            throw new OrderDomainException("OrderUpdateRequestDTO não pode ser nulo");
        }

        // O uso deste método para updates DEVE SER EVITADO.
        return Order.create(
                id,
                orderUpdateRequestDTO.dateTimeOrder(),
                orderUpdateRequestDTO.delivery(),
                restaurantId,
                userId,
                orderUpdateRequestDTO.lastModifiedDate(),
                null // O status original DEVE ser fornecido aqui, mas como é uma falha arquitetural, deixamos nulo ou PENDING.
                // Colocamos 'null' aqui para forçar a atenção, pois um UseCase deveria prover o status original.
                // Se precisar de um valor para compilar, use OrderStatus.PENDING (mas é semanticamente errado).
        );
    }
}
