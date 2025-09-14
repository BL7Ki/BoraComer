package pos.java.bora_comer.core.mapper.pedido.impl;

import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.errors.PedidoDomainException;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoRequestDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoResponseDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;

@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public Pedido toDomain(PedidoRequestDTO pedidoRequestDTO) {
        if (pedidoRequestDTO == null) {
            throw new PedidoDomainException("PedidoRequestDTO não pode ser nulo");
        }

        return Pedido.create(
                pedidoRequestDTO.dateTimeOrder(),
                pedidoRequestDTO.delivery(),
                pedidoRequestDTO.restaurantId(),
                pedidoRequestDTO.userId(),
                pedidoRequestDTO.lastModifiedDate()
        );
    }

    @Override
    public PedidoEntity toEntity(Pedido pedido) {
        if (pedido == null) {
            throw new PedidoDomainException("Pedido não pode ser nulo");
        }

        return PedidoEntity.create(
                pedido.getDateTimeOrder(),
                pedido.isDelivery(),
                pedido.getRestaurantId(),
                pedido.getUserId()
        );
    }

    @Override
    public Pedido toDomain(PedidoEntity pedidoEntity) {
        if (pedidoEntity == null) {
            throw new PedidoDomainException("PedidoEntity não pode ser nulo");
        }

        return Pedido.create(
                pedidoEntity.getId(),
                pedidoEntity.getDateTimeOrder(),
                pedidoEntity.isDelivery(),
                pedidoEntity.getRestaurantId(),
                pedidoEntity.getUserId(),
                pedidoEntity.getLastModifiedDate()
        );
    }

    @Override
    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        if (pedido == null) {
            throw new PedidoDomainException("Pedido não pode ser nulo");
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDateTimeOrder(),
                pedido.isDelivery(),
                pedido.getRestaurantId(),
                pedido.getUserId(),
                pedido.getLastModifiedDate()
        );
    }

    @Override
    public Pedido toDomain(PedidoUpdateRequestDTO pedidoUpdateRequestDTO, Long id, Long restaurantId, Long userId) {
        if (pedidoUpdateRequestDTO == null) {
            throw new PedidoDomainException("PedidoUpdateRequestDTO não pode ser nulo");
        }

        return Pedido.create(
                id,
                pedidoUpdateRequestDTO.dateTimeOrder(),
                pedidoUpdateRequestDTO.delivery(),  
                restaurantId, // preserva o restaurantId que vem do parâmetro
                userId, // preserva o userId que vem do parâmetro
                pedidoUpdateRequestDTO.lastModifiedDate()
        );
    }
}
