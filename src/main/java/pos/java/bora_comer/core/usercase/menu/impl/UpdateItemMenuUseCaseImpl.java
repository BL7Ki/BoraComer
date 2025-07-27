package pos.java.bora_comer.core.usercase.menu.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.gateway.menu.MenuItemUpdateGateway;
import pos.java.bora_comer.core.usercase.menu.UpdateMenuItemUseCase;

@Service
public class UpdateItemMenuUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemUpdateGateway menuItemUpdateGateway;

    public UpdateItemMenuUseCaseImpl(MenuItemUpdateGateway menuItemUpdateGateway) {
        this.menuItemUpdateGateway = menuItemUpdateGateway;
    }

    @Override
    public MenuItem execute(MenuItem menuItem) {
        return menuItemUpdateGateway.update(menuItem);
    }

    @Override
    public MenuItem findById(Long id) throws MenuItemDomainException {
        return menuItemUpdateGateway.findById(id)
                .orElseThrow(() -> new MenuItemDomainException("Item de menu com ID " + id + " não encontrado"));
    }
}
