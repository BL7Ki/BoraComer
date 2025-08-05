package pos.java.bora_comer.infra.gateway.menu.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.gateway.menu.MenuItemCreateGateway;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;

@Component
public class MenuItemCreateGatewayImpl implements MenuItemCreateGateway {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuItemCreateGatewayImpl(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public boolean existsByNameAndRestaurantId(String name, Long restaurantId) {
        return menuItemRepository.existsByNameAndRestaurantId(name, restaurantId);
    }

    @Transactional
    @Override
    public MenuItem save(MenuItem menuItem) {
        if (existsByNameAndRestaurantId(menuItem.getName(), menuItem.getRestaurantId())) {
            throw new MenuItemDomainException("Já existe um item de menu com esse nome para esse Restaurante.");
        }

        var menuItemEntity = menuItemMapper.toEntity(menuItem);
        var savedEntity = menuItemRepository.save(menuItemEntity);

        return menuItemMapper.toDomain(savedEntity);
    }
}
