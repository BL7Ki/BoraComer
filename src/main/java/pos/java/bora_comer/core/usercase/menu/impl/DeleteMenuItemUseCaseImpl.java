package pos.java.bora_comer.core.usercase.menu.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.gateway.menu.MenuItemDeleteGateway;
import pos.java.bora_comer.core.usercase.menu.DeleteMenuItemUseCase;

@Service
public class DeleteMenuItemUseCaseImpl implements DeleteMenuItemUseCase {

    private final MenuItemDeleteGateway menuItemDeleteGateway;

    public DeleteMenuItemUseCaseImpl(MenuItemDeleteGateway menuItemDeleteGateway) {
        this.menuItemDeleteGateway = menuItemDeleteGateway;
    }

    @Override
    public void execute(Long id) {
        menuItemDeleteGateway.deleteById(id);
    }
}
