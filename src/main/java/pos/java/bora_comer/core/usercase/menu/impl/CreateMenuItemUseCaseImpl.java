package pos.java.bora_comer.core.usercase.menu.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.gateway.menu.MenuItemCreateGateway;
import pos.java.bora_comer.core.usercase.menu.CreateMenuItemUseCase;


@Service
public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemCreateGateway menuItemCreateGateway;

    public CreateMenuItemUseCaseImpl(MenuItemCreateGateway menuItemCreateGateway) {
        this.menuItemCreateGateway = menuItemCreateGateway;
    }

    @Override
    public MenuItem execute(MenuItem menuItem) {
        return menuItemCreateGateway.save(menuItem);
    }
}
