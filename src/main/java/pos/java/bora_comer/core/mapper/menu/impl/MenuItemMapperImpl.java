package pos.java.bora_comer.core.mapper.menu.impl;

import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;

@Component
public class MenuItemMapperImpl implements MenuItemMapper {

    @Override
    public MenuItem toDomain(MenuItemRequestDTO menuItemRequestDTO) {
        if (menuItemRequestDTO == null) {
            throw new MenuItemDomainException("MenuItemRequestDTO não pode ser nulo");
        }

        return MenuItem.create(
                menuItemRequestDTO.name(),
                menuItemRequestDTO.description(),
                menuItemRequestDTO.price(),
                menuItemRequestDTO.inPlaceOnly(),
                menuItemRequestDTO.imagePath(),
                menuItemRequestDTO.restaurantId()
        );
    }

    @Override
    public MenuItemEntity toEntity(MenuItem menuItem) {
        if (menuItem == null) {
            throw new MenuItemDomainException("MenuItem não pode ser nulo");
        }

        return MenuItemEntity.create(
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isInPlaceOnly(),
                menuItem.getImagePath(),
                menuItem.getRestaurantId()
        );
    }

    @Override
    public MenuItem toDomain(MenuItemEntity menuItemEntity) {
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
