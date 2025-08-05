package pos.java.bora_comer.core.usercase.menu;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;

public interface UpdateMenuItemUseCase {
    MenuItem execute(MenuItem menuItem) throws MenuItemDomainException;

    MenuItem findById(Long id) throws MenuItemDomainException;
}
