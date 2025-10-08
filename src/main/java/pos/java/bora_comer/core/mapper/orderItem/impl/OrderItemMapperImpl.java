package pos.java.bora_comer.core.mapper.orderItem.impl;

import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemRequestDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItem toDomain(OrderItemRequestDTO orderItemRequestDTO) {
        if (orderItemRequestDTO == null) {
            throw new OrderItemDomainException("OrderItemRequestDTO não pode ser nulo");
        }

        return OrderItem.create(
                null, // ID será gerado pelo banco
                orderItemRequestDTO.orderId(),
                orderItemRequestDTO.menuItemId(),
                orderItemRequestDTO.quantity(),
                null // lastModifiedDate será gerado na criação
        );
    }

    @Override
    public OrderItemEntity toEntity(OrderItem orderItem) {
        if (orderItem == null) {
            throw new OrderItemDomainException("Item do Pedido não pode ser nulo");
        }

        return OrderItemEntity.create(
                orderItem.getOrderId(),
                orderItem.getMenuItemId(),
                orderItem.getQuantity()
        );
    }

    @Override
    public OrderItem toDomain(OrderItemEntity orderEntity) {
        if (orderEntity == null) {
            throw new OrderItemDomainException("OrderItemEntity não pode ser nulo");
        }

        return OrderItem.create(
                orderEntity.getId(),
                orderEntity.getOrderId(),
                orderEntity.getMenuItemId(),
                orderEntity.getQuantity(),
                orderEntity.getLastModifiedDate()
        );
    }

    @Override
    public OrderItemResponseDTO toResponseDTO(OrderItem orderItem) {
        if (orderItem == null) {
            throw new OrderItemDomainException("Item do Pedido não pode ser nulo");
        }

        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getOrderId(),
                orderItem.getMenuItemId(),
                orderItem.getQuantity(),
                orderItem.getLastModifiedDate()
        );
    }

    @Override
    public OrderItem toDomain(OrderItemUpdateRequestDTO orderItemUpdateRequestDTO, Long id, Long orderId, Long menuItemId) {
        if (orderItemUpdateRequestDTO == null) {
            throw new OrderItemDomainException("OrderItemUpdateRequestDTO não pode ser nulo");
        }

        return OrderItem.create(
                id,
                orderId,
                menuItemId,
                orderItemUpdateRequestDTO.quantity(),
                null // lastModifiedDate será atualizado na atualização
        );
    }
}
