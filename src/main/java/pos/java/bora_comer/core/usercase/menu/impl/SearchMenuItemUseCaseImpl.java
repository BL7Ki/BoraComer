package pos.java.bora_comer.core.usercase.menu.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.gateway.menu.MenuItemSearchGateway;
import pos.java.bora_comer.core.usercase.menu.SearchMenuItemUseCase;


@Service
public class SearchMenuItemUseCaseImpl implements SearchMenuItemUseCase {

    private final MenuItemSearchGateway menuItemSearchGateway;

    public SearchMenuItemUseCaseImpl(MenuItemSearchGateway menuItemSearchGateway) {
        this.menuItemSearchGateway = menuItemSearchGateway;
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemSearchGateway.findById(id);
    }

    @Override
    public Page<MenuItem> findAll(int page, int size) {
        return menuItemSearchGateway.findAll(page, size);
    }
}
