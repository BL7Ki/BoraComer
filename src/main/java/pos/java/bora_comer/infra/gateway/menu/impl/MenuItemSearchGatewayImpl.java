package pos.java.bora_comer.infra.gateway.menu.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.menu.MenuItemSearchGateway;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;


@Component
public class MenuItemSearchGatewayImpl implements MenuItemSearchGateway {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuItemSearchGatewayImpl(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
                .map(menuItemMapper::toDomain)
                .orElseThrow(() -> new SummerNotFoundException("Item do menu com ID " + id + " não encontrado."));
    }

    @Override
    public Page<MenuItem> findAll(int page, int size) {
        return menuItemRepository.findAll(PageRequest.of(page, size))
                .map(menuItemMapper::toDomain);
    }
}
