package pos.java.bora_comer.core.mapper.pedido.impl;

import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.pedido.Pedido;
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
            
        );
    }

    @Override
    public PedidoEntity toEntity(Pedido pedido) {
        if (menuItem == null) {
            throw new MenuItemDomainException("MenuItem não pode ser nulo");
        }

        return PedidoEntity.create(
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isInPlaceOnly(),
                menuItem.getImagePath(),
                menuItem.getRestaurantId()
        );
    }

    @Override
    public MenuItem toDomain(PedidoEntity menuItemEntity) {
        if (menuItemEntity == null) {
            throw new MenuItemDomainException("MenuItemEntity não pode ser nulo");
        }

        return MenuItem.create(
                menuItemEntity.getId(),
                menuItemEntity.getName(),
                menuItemEntity.getDescription(),
                menuItemEntity.getPrice(),
                menuItemEntity.isInPlaceOnly(),
                menuItemEntity.getImagePath(),
                menuItemEntity.getRestaurantId()
        );
    }

    @Override
    public MenuItemResponseDTO toResponseDTO(MenuItem menuItem) {
        if (menuItem == null) {
            throw new MenuItemDomainException("MenuItem não pode ser nulo");
        }

        return new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isInPlaceOnly(),
                menuItem.getImagePath(),
                menuItem.getRestaurantId()
        );
    }

    @Override
    public MenuItem toDomain(MenuItemUpdateRequestDTO menuItemUpdateRequestDTO, Long id, Long restaurantId) {
        if (menuItemUpdateRequestDTO == null) {
            throw new MenuItemDomainException("MenuItemUpdateRequestDTO não pode ser nulo");
        }

        return MenuItem.create(
                id,
                menuItemUpdateRequestDTO.name(),
                menuItemUpdateRequestDTO.description(),
                menuItemUpdateRequestDTO.price(),
                menuItemUpdateRequestDTO.inPlaceOnly(),
                menuItemUpdateRequestDTO.imagePath(),
                restaurantId // preserva o restaurantId que vem do parâmetro
        );
    }
}
