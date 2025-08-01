package pos.java.bora_comer.infra.gateway.menu.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.gateway.menu.MenuItemUpdateGateway;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;

import java.util.Optional;

@Component
public class MenuItemUpdateGatewayImpl implements MenuItemUpdateGateway {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;
    private final RestaurantRepository restaurantRepository;

    public MenuItemUpdateGatewayImpl(MenuItemRepository menuItemRepository,
                                     MenuItemMapper menuItemMapper,
                                     RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @Override
    public MenuItem update(MenuItem menuItem) throws MenuItemDomainException {
        MenuItemEntity entity = menuItemRepository.findById(menuItem.getId())
                .orElseThrow(() -> new MenuItemDomainException("Item do menu com ID " + menuItem.getId() + " não encontrado."));

        if (!menuItem.getRestaurantId().equals(entity.getRestaurantId())) {
            restaurantRepository.findById(menuItem.getRestaurantId())
                    .orElseThrow(() -> new MenuItemDomainException("Restaurante com ID " + menuItem.getRestaurantId() + " não encontrado."));
            entity.updateRestaurantId(menuItem.getRestaurantId());
        }

        entity.updateName(menuItem.getName());
        entity.updateDescription(menuItem.getDescription());
        entity.updatePrice(menuItem.getPrice());
        entity.updateInPlaceOnly(menuItem.isInPlaceOnly());
        entity.updateImagePath(menuItem.getImagePath());
        entity.updateLastModifiedDate();

       MenuItemEntity updatedEntity = menuItemRepository.save(entity);
        return menuItemMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return menuItemRepository.findById(id)
                .map(menuItemMapper::toDomain);
    }
}
