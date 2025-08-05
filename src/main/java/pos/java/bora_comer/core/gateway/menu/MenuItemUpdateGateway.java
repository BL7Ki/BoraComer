package pos.java.bora_comer.core.gateway.menu;

import java.util.Optional;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;

public interface MenuItemUpdateGateway {

    MenuItem update(MenuItem menuItem) throws MenuItemDomainException;

    Optional<MenuItem> findById(Long id);
}
