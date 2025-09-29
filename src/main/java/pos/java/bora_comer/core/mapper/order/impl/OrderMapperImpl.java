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

        return OrderEntity.create(
                order.getDateTimeOrder(),
                order.isDelivery(),
                order.getRestaurantId(),
                order.getUserId() 
        );
    }

    @Override
    public Order toDomain(OrderEntity orderEntity) {
        if (orderEntity == null) {
            throw new OrderDomainException("OrderEntity não pode ser nulo");
        }

        return Order.create(
                orderEntity.getId(),
                orderEntity.getDateTimeOrder(),
                orderEntity.isDelivery(),
                orderEntity.getRestaurantId(),
                orderEntity.getUserId(),
                orderEntity.getLastModifiedDate()
        );
    }

    @Override
    public OrderResponseDTO toResponseDTO(Order order) {
        if (order == null) {
            throw new OrderDomainException("Pedido não pode ser nulo");
        }

        return new OrderResponseDTO(
                order.getId(),
                order.getDateTimeOrder(),
                order.isDelivery(),
                order.getRestaurantId(),
                order.getUserId(),
                order.getLastModifiedDate()
        );
    }

    @Override
    public Order toDomain(OrderUpdateRequestDTO orderUpdateRequestDTO, Long id, Long restaurantId, Long userId) {
        if (orderUpdateRequestDTO == null) {
            throw new OrderDomainException("OrderUpdateRequestDTO não pode ser nulo");
        }

        return Order.create(
                id,
                orderUpdateRequestDTO.dateTimeOrder(),
                orderUpdateRequestDTO.delivery(),  
                restaurantId, // preserva o restaurantId que vem do parâmetro
                userId, // preserva o userId que vem do parâmetro
                orderUpdateRequestDTO.lastModifiedDate()
        );
    }
}
