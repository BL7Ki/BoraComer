package pos.java.bora_comer.core.gateway.menu;

import pos.java.bora_comer.core.domain.menu.MenuItem;

public interface MenuItemCreateGateway {

    boolean existsByName(String name);

    MenuItem save(MenuItem menuItem);
}
