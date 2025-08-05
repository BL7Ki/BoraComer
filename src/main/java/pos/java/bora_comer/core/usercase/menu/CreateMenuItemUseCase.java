package pos.java.bora_comer.core.usercase.menu;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;

public interface CreateMenuItemUseCase {

    MenuItem execute(MenuItem menuItem) throws MenuItemDomainException;
}
